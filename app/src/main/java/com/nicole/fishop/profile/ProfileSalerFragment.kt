package com.nicole.fishop.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.request.RequestOptions
import com.nicole.fishop.GlideApp
import com.nicole.fishop.R
import com.nicole.fishop.data.TimeChangFormat
import com.nicole.fishop.data.Users
import com.nicole.fishop.databinding.FragmentProfileSellerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger
import com.nicole.fishop.util.Logger.d
import com.nicole.fishop.util.Logger.i
import java.text.SimpleDateFormat
import java.util.*


class ProfileSalerFragment : Fragment() {

    private val viewModel by viewModels<ProfileSalerViewModel> {
        getVmFactory(
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileSellerBinding.inflate(inflater)
        Log.d("Nicole", "ProfileSalerFragment onCreateView")


        viewModel.users.observe(viewLifecycleOwner, Observer { user ->
            binding.textViewAddress.text = user.address
            binding.textViewPhone.text = user.phone
            binding.toolbarTitle.text = user.name
            TimeChangFormat.bindImageWithCircleCrop(binding.imageViewMine, user.picture)

            UserManager.user?.name = viewModel.users.value?.name

            Logger.i("viewModel.users ${viewModel.users.value}")
            val startTime = user.businessTime?.let { it1 -> getNowTime(it1.toLong()) }
            val stopTime = user.businessEndTime?.let { it1 -> getNowTime(it1.toLong()) }
            if (user.businessDay?.contains("星期一") == true) {
                binding.textViewMondaystatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewMondaystatus.text = "休息"
            }

            if (user.businessDay?.contains("星期二") == true) {
                binding.textViewTuesdaystatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewTuesdaystatus.text = "休息"
            }

            if (user.businessDay?.contains("星期三") == true) {
                binding.textViewWednesdayStatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewWednesdayStatus.text = "休息"
            }

            if (user.businessDay?.contains("星期四") == true) {
                binding.textViewThursdayStatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewThursdayStatus.text = "休息"
            }

            if (user.businessDay?.contains("星期五") == true) {
                binding.textViewFridayStatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewFridayStatus.text = "休息"
            }

            if (user.businessDay?.contains("星期六") == true) {
                binding.textViewSaturdayStatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewSaturdayStatus.text = "休息"
            }

            if (user.businessDay?.contains("星期日") == true) {
                binding.textViewSundayStatus.text = "$startTime ~ $stopTime"
            } else {
                binding.textViewSundayStatus.text = "休息"
            }


        })


        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNowTime(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("HH:mm").format(time)
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