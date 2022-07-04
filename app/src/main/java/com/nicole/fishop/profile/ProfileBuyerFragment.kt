package com.nicole.fishop.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nicole.fishop.databinding.FragmentProfileBuyerBinding
import com.nicole.fishop.ext.getVmFactory


class ProfileBuyerFragment : Fragment() {

    private val viewModel by viewModels<ProfileBuyerViewModel> {
        getVmFactory(
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBuyerBinding.inflate(inflater)




        return binding.root
    }


}