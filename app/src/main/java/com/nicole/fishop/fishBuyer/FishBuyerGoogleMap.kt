package com.nicole.fishop.fishBuyer

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.extensions.moveCameraOnMap
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentFishBuyerGoogleMapBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger
import java.util.*


class FishBuyerGoogleMap() : Fragment(), OnMapReadyCallback {

    private val viewModel by viewModels<FishBuyerGoogleMapViewModel> { getVmFactory() }
    private var mMap: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fishToday = FishBuyerGoogleMapArgs.fromBundle(
            requireArguments()
        ).addressKey

        Logger.d("FishBuyerGoogleMapArgs fishToday $fishToday")
        viewModel.fishToday = fishToday

        val binding = FragmentFishBuyerGoogleMapBinding.inflate(layoutInflater)

        viewModel.getGoogleMapResult(fishToday.ownerId)
        viewModel.sellerLocation.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Logger.d(" it.address ${it.address}")
            binding.editTextSellerLocation.text = it.address.toString()
            val callback = OnMapReadyCallback { googleMap ->

                var geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
                val addressLocation: List<Address> = geoCoder!!.getFromLocationName(it.address, 1)
                val latitude = addressLocation[0].latitude
                val longitude = addressLocation[0].longitude

                val startLocation: List<Address> = geoCoder!!.getFromLocationName("台北市臨沂街74巷2弄", 1)
                val startlatitude = startLocation[0].latitude
                val startlongitude = startLocation[0].longitude

                val source = LatLng(startlatitude, startlongitude) //starting point (LatLng)
                val destination = LatLng(latitude, longitude) // ending point (LatLng)

                googleMap.run {
                    moveCameraOnMap(latLng = source) // if you want to zoom the map to any point

                    //Called the drawRouteOnMap extension to draw the polyline/route on google maps
                    drawRouteOnMap(
                        getString(R.string.google_map_api_key), //your API key
                        source = source, // Source from where you want to draw path
                        destination = destination, // destination to where you want to draw path
                        context = requireContext() //Activity context
                    )

                }
            }
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getURL(from: LatLng, to: LatLng): String {
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest&$sensor"
        return "https://maps.googleapis.com/maps/api/directions/json?$params"
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }

        return poly
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // declare bounds object to fit whole route in screen
        val LatLongB = LatLngBounds.Builder()

        // Add markers
        val sydney = LatLng(-34.0, 151.0)
        val opera = LatLng(-33.9320447, 151.1597271)


    }

}