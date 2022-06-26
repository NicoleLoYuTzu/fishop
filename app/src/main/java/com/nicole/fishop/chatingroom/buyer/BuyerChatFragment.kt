package com.nicole.fishop.chatingroom.buyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicole.fishop.R
import com.nicole.fishop.databinding.FragmentChatBuyerBinding


class BuyerChatFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentChatBuyerBinding.inflate(layoutInflater)
        binding.recyclerView.adapter=
        return binding.root
    }


}