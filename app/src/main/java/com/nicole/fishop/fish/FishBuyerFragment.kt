package com.nicole.fishop.fish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentFishBuyerBinding
import com.nicole.fishop.databinding.FragmentProfileBuyerBinding


class FishBuyerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishBuyerBinding.inflate(inflater)
        return binding.root
    }


}