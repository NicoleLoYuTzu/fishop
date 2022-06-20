package com.nicole.fishop.fishSeller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayBinding
import com.nicole.fishop.databinding.FragmentFishSellerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger


class FishSellerFragmentAddToday : Fragment() {

    private val viewModel by viewModels<FishSellerViewModelAddToday> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishSellerAddTodayBinding.inflate(inflater)
        binding.recyclerView.adapter = FishSellerAddTodayAdapter()
        viewModel.fishAll.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.fishAll.observe")
            (binding.recyclerView.adapter as FishSellerAddTodayAdapter).submitList(it)
        })

        return binding.root
    }

}