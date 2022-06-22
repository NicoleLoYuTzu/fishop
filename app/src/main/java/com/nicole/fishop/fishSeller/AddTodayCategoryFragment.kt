package com.nicole.fishop.fishSeller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*


class AddTodayCategoryFragment : Fragment() {

    private val viewModel by viewModels<AddTodayCategoryViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishSellerAddTodayBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.recyclerView.adapter = AddTodayCategoryItemAdapter()

//        viewModel.fishtodayItem.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            (binding.recyclerView.adapter as AddTodayCategoryItemAdapter).submitList(a)
////            binding.recyclerView.adapter = AddTodayCategoryItemAdapter()
//        })

        viewModel.fishAll.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            (binding.recyclerView.adapter as AddTodayCategoryItemAdapter).submitList(it)
        })


        binding.textViewToday.text= getNow()
        binding.buttonSave.setOnClickListener {
//            findNavController().navigate(NavFragmentDirections.actionFishSellerFragmentToFishSellerFragmentAddToday())
            findNavController().popBackStack()
        }



        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    fun getNow(): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("yyyy/MM/dd").format(Date())
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
        }
    }

}