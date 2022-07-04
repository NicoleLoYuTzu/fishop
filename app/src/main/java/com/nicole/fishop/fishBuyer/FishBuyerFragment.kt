package com.nicole.fishop.fishBuyer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.maps.route.extensions.drawRouteOnMap
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.R
import com.nicole.fishop.REQUEST_ENABLE_GPS
import com.nicole.fishop.REQUEST_LOCATION_PERMISSION
import com.nicole.fishop.data.SellerLocation
import com.nicole.fishop.databinding.FragmentFishBuyerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit


class FishBuyerFragment : Fragment() {

    private lateinit var list: MutableList<String>

    private var startLocationFromBuyerPosition: LatLng = startLocation(0.0, 0.0)
    private var locationPermissionGranted = false

    private lateinit var mContext: Context
    private lateinit var mLocationProviderClient: FusedLocationProviderClient
    private val viewModel by viewModels<FishBuyerViewModel> { getVmFactory() }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            delay(TimeUnit.SECONDS.toMillis(3))
            withContext(Dispatchers.Main) {
                getDeviceLocation()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = requireActivity()
        mLocationProviderClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        getLocationPermission()

        // Inflate the layout for this fragment
        val binding = FragmentFishBuyerBinding.inflate(inflater)
//        viewModel.getFishTodayFilterResult("")
        viewModel.fishToday.observe(viewLifecycleOwner, Observer {
            (binding.recyclerView.adapter as FishBuyerAdapter).submitList(it)
            (binding.recyclerView.adapter as FishBuyerAdapter).notifyDataSetChanged()
            //把所有owernerId帶入

            val ownerIds = mutableListOf<String>()
            for (i in it) {
                Logger.d("i.ownerId ${i.ownerId} , i => $i")
                ownerIds.add(i.ownerId)
            }

            viewModel.getAllSellerAddressResult(ownerIds)
            Logger.d("ownerIds $ownerIds ")

            Logger.d(" viewModel.fishToday.observe $it ")
        })



        binding.recyclerView.adapter = FishBuyerAdapter(
            FishBuyerAdapter.OnClickListener {
                viewModel.navigateToGoogleMap(it)
            },
        )

        viewModel.navigateToGoogleMap.observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController(binding.root).navigate(
                        NavFragmentDirections.actionToFishBuyerGoogleMap(
                            it
                        )
                    )
                    viewModel.onGoogleMapNavigated()
                }
            }
        )

        /**
         * Set up search view with list view to show the user enter text
         */
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                viewModel.getFishTodayFilterResult(p0)
                Logger.d("searchView p0 $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Logger.d("onQueryTextChange p0 $p0")
                if (p0 == null || p0 == "") {
                    viewModel.getFishTodayAllResult()
                }
                return false
            }

        })

        viewModel.sellerLocations.observe(viewLifecycleOwner) {
            Logger.i("LIVEDATA SELLERLOCATION = $it")
        }

        viewModel.startLocation.observe(viewLifecycleOwner, Observer {

                viewModel.sellerLocations.value?.let { locations ->
                    for (location in locations) {

                        Logger.i("location = $location")
                        Logger.i("location.name = ${location.name}")

                        val distance = getDistance(location)
                        Logger.d("distance => $distance")

                        val foundToday = viewModel.fishToday.value?.find {
                            it.ownerId == location.id
                        }
                        Logger.d("foundToday => $foundToday")
                        foundToday?.distance = distance.toLong()
                        Logger.d("foundToday after assign => $foundToday")
                    }
                    viewModel._fishToday.value = viewModel._fishToday.value
                }

        })



        return binding.root
    }


    fun calculateDistance(
        startlatitude: Double,
        startlongitude: Double,
        stoplatitude: Double,
        stoplongitude: Double
    ): Float {

        val startPoint = Location("locationA")
        startPoint.latitude = startlatitude
        startPoint.longitude = startlongitude

        val endPoint = Location("locationB")
        endPoint.latitude = stoplatitude
        endPoint.longitude = stoplongitude

        return startPoint.distanceTo(endPoint)
    }

    fun getLocationPermission() {
        Logger.d("getLocationPermission")
        //檢查權限
        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            //已獲取到權限
            Toast.makeText(activity, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            locationPermissionGranted = true

            checkGPSState()
        } else {
            requestLocationPermission()
        }
    }

    private fun checkGPSState() {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(mContext)
                .setTitle("GPS 尚未開啟")
                .setMessage("使用此功能需要開啟 GSP 定位功能")
                .setPositiveButton("前往開啟",
                    DialogInterface.OnClickListener { _, _ ->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS
                        )
                    })
                .setNegativeButton("取消", null)
                .show()
        } else {
            Toast.makeText(activity, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            getDeviceLocation()
        }
    }

    fun startLocation(startlatitude: Double, startlongitude: Double): LatLng {
        val destination = LatLng(startlatitude, startlongitude) // ending point (LatLng)
        return destination
    }

    fun getDeviceLocation() {
        try {
            if (locationPermissionGranted
            ) {
                val locationRequest = LocationRequest()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                //更新頻率
                locationRequest.interval = 1000
                //更新次數，若沒設定，會持續更新
                //locationRequest.numUpdates = 1
                mLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            p0 ?: return
                            Log.d(
                                "HKT",
                                "緯度:${p0.lastLocation?.latitude} , 經度:${p0.lastLocation?.longitude} "

                            )
//                            var startlatitude = p0.lastLocation?.latitude!!
//                            var startlongitude = p0.lastLocation?.longitude!!
                            startLocationFromBuyerPosition =
                                startLocation(
                                    p0.lastLocation?.latitude!!,
                                    p0.lastLocation?.longitude!!
                                )
                            viewModel.startLocation.value = startLocationFromBuyerPosition

                            // self location ready -> use googleMap do something
                        }
                    },
                    null
                )

            } else {
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun requestLocationPermission() {
        Logger.d("requestLocationPermission")
        if (activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it, Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == true
        ) {
            AlertDialog.Builder(requireActivity())
                .setMessage("此應用程式，需要位置權限才能正常使用")
                .setPositiveButton("確定") { _, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                .show()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        Logger.d("onRequestPermissionsResult")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //已獲取到權限
                        locationPermissionGranted = true
                        //todo checkGPSState()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!activity?.let {
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    it,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                            }!!
                        ) {
                            //權限被永久拒絕
                            Toast.makeText(activity, "位置權限已被關閉，功能將會無法正常使用", Toast.LENGTH_SHORT)
                                .show()

                            activity?.let {
                                AlertDialog.Builder(it)
                                    .setTitle("開啟位置權限")
                                    .setMessage("此應用程式，位置權限已被關閉，需開啟才能正常使用")
                                    .setPositiveButton("確定") { _, _ ->
                                        val intent =
                                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                        startActivityForResult(intent, REQUEST_LOCATION_PERMISSION)
                                    }
                                    .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                                    .show()
                            }
                        } else {
                            //權限被拒絕
                            Toast.makeText(activity, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT)
                                .show()
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("onActivityResult")
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                getLocationPermission()
            }
        }
    }

    fun getDistance(sellerLocation: SellerLocation): Float {


            val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
            val addressLocation: List<Address> =
                geoCoder!!.getFromLocationName(sellerLocation.address, 1)
            Logger.i("addressLocation $addressLocation")
            val distance = calculateDistance(
                startLocationFromBuyerPosition.latitude,
                startLocationFromBuyerPosition.longitude,
                addressLocation[0].latitude,
                addressLocation[0].longitude
            )
            Logger.d("it.address=>  ${sellerLocation.address} distance =>${distance}米")
            Logger.d("addressLocation[0].latitude ${addressLocation[0].latitude}")
            Logger.d("addressLocation[0].longitude ${addressLocation[0].longitude}")

            Logger.d("startLocationFromBuyerPosition.latitude, ${startLocationFromBuyerPosition.latitude}")
            Logger.d("startLocationFromBuyerPosition.longitude, ${startLocationFromBuyerPosition.longitude}")


        return distance
    }
}