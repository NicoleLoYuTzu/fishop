package com.nicole.fishop.fishBuyer

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentFishBuyerGoogleMapBinding
import com.nicole.fishop.ext.getVmFactory
import java.util.*


class FishBuyerGoogleMap : Fragment() {

    private val viewModel by viewModels<FishBuyerViewModel> { getVmFactory() }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        var geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
        val addressLocation: List<Address> = geoCoder!!.getFromLocationName("桃園市八德區桃德路488號", 1)
        val latitude: Double = addressLocation[0].latitude
        val longitude: Double = addressLocation[0].longitude
        val sydney = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in 我家"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFishBuyerGoogleMapBinding.inflate(layoutInflater)
        binding.editTextSellerLocation.text
        viewModel.getGoogleMapResult("123")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}