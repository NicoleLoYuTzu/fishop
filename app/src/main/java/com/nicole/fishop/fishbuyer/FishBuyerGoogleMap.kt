package com.nicole.fishop.fishbuyer

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import com.nicole.fishop.R
import com.nicole.fishop.REQUEST_ENABLE_GPS
import com.nicole.fishop.REQUEST_LOCATION_PERMISSION
import com.nicole.fishop.databinding.FragmentFishBuyerGoogleMapBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger
import java.util.*

class FishBuyerGoogleMap() : Fragment(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private var startLocationFromBuyerPosition: LatLng = startLocation(0.0, 0.0)
    private var stopLocationToSalerPosition: LatLng = stopLocation(0.0, 0.0)
    private val viewModel by viewModels<FishBuyerGoogleMapViewModel> { getVmFactory() }

    private var locationPermissionGranted = false

    private lateinit var mContext: Context
    private lateinit var mLocationProviderClient: FusedLocationProviderClient

    var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = requireActivity()
        mLocationProviderClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        // Dynamically apply for required permissions if the API level is 28 or smaller.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            Log.i(TAG, "android sdk <= 28 Q")
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(requireActivity(), strings, 1)
            }
        } else {
            // Dynamically apply for required permissions if the API level is greater than 28. The android.permission.ACCESS_BACKGROUND_LOCATION permission is required.
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        "android.permission.ACCESS_BACKGROUND_LOCATION"
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                )

                ActivityCompat.requestPermissions(requireActivity(), strings, 2)
            }
        }

        getLocationPermission()
        getDeviceLocation()

        val fishToday =FishBuyerGoogleMapArgs.fromBundle(
            requireArguments()
        ).addressKey

        Logger.d("FishBuyerGoogleMapArgs fishToday $fishToday")
        viewModel.fishToday = fishToday

        val binding = FragmentFishBuyerGoogleMapBinding.inflate(layoutInflater)

        viewModel.getGoogleMapResult(fishToday.ownerId)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewModel.sellerLocation.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                // seller location ready -> use googleMap do something
                Logger.d(" it.address ${it.address}")
                binding.editTextSellerLocation.text = it.address.toString()
                val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
                val addressLocation: List<Address> = geoCoder!!.getFromLocationName(it.address, 1)
                val stopLatitude = addressLocation[0].latitude
                val stopLongitude = addressLocation[0].longitude
                stopLocationToSalerPosition = stopLocation(stopLatitude, stopLongitude)
                googleMap?.run {
                    moveCameraOnMap(latLng = stopLocationToSalerPosition)
                }
                googleMap?.addMarker(
                    MarkerOptions().position(stopLocationToSalerPosition).draggable(true).title(it.name)
                )
                googleMap?.setOnMarkerClickListener(this)
            }
        )

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

    fun startLocation(startlatitude: Double, startlongitude: Double): LatLng {
        val destination = LatLng(startlatitude, startlongitude) // ending point (LatLng)
        return destination
    }

    fun stopLocation(stoplatitude: Double, stoplongitude: Double): LatLng {
        val source = LatLng(stoplatitude, stoplongitude)
        return source
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap) {
        // map ready
        val defaultLocation = LatLng(25.0338483, 121.5645283)
        googleMap = map
        googleMap!!.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                defaultLocation, 6f
            )
        )
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocationPermission() {
        Logger.d("getLocationPermission")
        // 檢查權限
        if (activity?.let {
            ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
        } == PackageManager.PERMISSION_GRANTED
        ) {
            // 已獲取到權限
            Toast.makeText(activity, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            locationPermissionGranted = true

            checkGPSState()
        } else {
            // 詢問要求獲取權限
            requestLocationPermission()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun checkGPSState() {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(mContext)
                .setTitle("GPS 尚未開啟")
                .setMessage("使用此功能需要開啟 GSP 定位功能")
                .setPositiveButton(
                    "前往開啟",
                    DialogInterface.OnClickListener { _, _ ->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS
                        )
                    }
                )
                .setNegativeButton("取消", null)
                .show()
        } else {
            Toast.makeText(activity, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            getDeviceLocation()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getDeviceLocation() {
        try {
            if (locationPermissionGranted
            ) {
                val locationRequest = LocationRequest()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                // 更新頻率
                locationRequest.interval = 1000
                // 更新次數，若沒設定，會持續更新
                // locationRequest.numUpdates = 1
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
                            googleMap?.run {
//                                moveCameraOnMap(latLng = startLocationFromBuyerPosition)
                                activity?.let {
                                    drawRouteOnMap(
                                        getString(R.string.google_map_api_key), // your API key
                                        source = startLocationFromBuyerPosition, // Source from where you want to draw path
                                        destination = stopLocationToSalerPosition, // destination to where you want to draw path
                                        context = it // Activity context
                                    )
                                }
                                val distance = calculateDistance(startLocationFromBuyerPosition.latitude, startLocationFromBuyerPosition.longitude, stopLocationToSalerPosition.latitude, stopLocationToSalerPosition.longitude)
                                Logger.d("distance $distance")
                            }

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
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Logger.d("onRequestPermissionsResult")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 已獲取到權限
                        locationPermissionGranted = true
                        // todo checkGPSState()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!activity?.let {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                    it,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                        }!!
                        ) {
                            // 權限被永久拒絕
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
                            // 權限被拒絕
                            Toast.makeText(activity, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT)
                                .show()
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("onActivityResult")
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                getLocationPermission()
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {

        viewModel.sellerLocation.value?.let {
            it.name = p0.title
        }
        return false
    }
}
