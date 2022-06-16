package com.nicole.fishop.fish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentFishBuyerBinding
import com.nicole.fishop.databinding.FragmentFishSellerBinding
import com.nicole.fishop.ext.getVmFactory


class FishSellerFragment : Fragment() {

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

        viewModel.getFishRecordResult()
        viewModel.fishRecord.observe(viewLifecycleOwner, Observer {

//            viewModel.getFishRecordResult()
        }
        )


        return binding.root
    }


}