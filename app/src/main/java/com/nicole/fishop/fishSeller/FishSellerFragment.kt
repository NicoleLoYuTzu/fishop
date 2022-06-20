package com.nicole.fishop.fishSeller

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentFishSellerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger


class FishSellerFragment() : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private val viewModel by viewModels<FishSellerViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishSellerBinding.inflate(inflater)

        binding.recyclerView.adapter = FishSellerAdapter(FishSellerAdapter.OnClickListener {
//            viewModel.navigateToDetail(it)
        })
        Logger.d("viewModel.fishRecord ${viewModel.fishRecord.value}")

        viewModel.fishRecord.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.fishRecord.observe")
            (binding.recyclerView.adapter as FishSellerAdapter).submitList(it)
        }
        )

        binding.imageViewAdd.setOnClickListener {
            findNavController().navigate(NavFragmentDirections.actionFishSellerFragmentToFishSellerFragmentAddToday())
        }




        return binding.root
    }


}