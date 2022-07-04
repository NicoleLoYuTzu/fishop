package com.nicole.fishop.profile

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentProfileBuyerEditBinding
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*


class ProfileSalerEditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileBuyerEditBinding.inflate(inflater)
        binding.editTextStartTime.transformIntoTimePicker(requireContext(), "HH:mm")
        binding.editTextStopTime.transformIntoTimePicker(requireContext(), "HH:mm")




        val businessDay: List<String> = emptyList()

        val mutableArray = businessDay.toMutableList()




        binding.checkBoxMonday.setOnCheckedChangeListener { compoundButton, b ->
            //星期一
            if (binding.checkBoxMonday.isChecked) {
                mutableArray.add(binding.checkBoxMonday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxMonday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }

        Logger.d("mutableArray $mutableArray ")

        binding.checkBoxTuesday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxTuesday.isChecked) {
                mutableArray.add(binding.checkBoxTuesday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxTuesday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }
        binding.checkBoxWednesday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxWednesday.isChecked) {
                mutableArray.add(binding.checkBoxWednesday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxWednesday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }
        binding.checkBoxThursday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxThursday.isChecked) {
                mutableArray.add(binding.checkBoxThursday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxThursday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }
        binding.checkBoxFriday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxFriday.isChecked) {
                mutableArray.add(binding.checkBoxFriday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxFriday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }
        binding.checkBoxSaturday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxSaturday.isChecked) {
                mutableArray.add(binding.checkBoxSaturday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxSaturday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }
        binding.checkBoxSunday.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxSunday.isChecked) {
                mutableArray.add(binding.checkBoxSunday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            } else {
                mutableArray.remove(binding.checkBoxSunday.text.toString())
                Logger.d("mutableArray $mutableArray ")
            }
        }





//        Logger.d("onCreateView businessday=> $mutableArray, startTimeToLong=> $startTimeToLong, stopTimeToLong $stopTimeToLong,binding.editTextShopname.text.toString()=> ${binding.editTextShopname.text.toString()}, binding.editTextPhone.text.toString(=>${binding.editTextPhone.text.toString()}")
        binding.buttonSave.setOnClickListener {
            context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("確定儲存?")
                    .setPositiveButton("確定") { dialog, _ ->
                        UserManager.user.let {
                            val startTime = binding.editTextStartTime.text.toString()
                            val stopTime = binding.editTextStopTime.text.toString()
                            val startTimeToLong = startTime.toTimeLong()
                            val stopTimeToLong = stopTime.toTimeLong()
                            Logger.d("確定 businessday=> $mutableArray, startTimeToLong=> $startTimeToLong, stopTimeToLong $stopTimeToLong,binding.editTextShopname.text.toString()=> ${binding.editTextShopname.text.toString()}, binding.editTextPhone.text.toString(=>${binding.editTextPhone.text.toString()}")
                            it?.businessDay = mutableArray
                            it?.businessEndTime = stopTimeToLong.toString()
                            it?.businessTime = startTimeToLong.toString()
                            it?.name = binding.editTextShopname.text.toString()
                            it?.phone = binding.editTextPhone.text.toString()
                        }


                        dialog.dismiss()
                        findNavController().navigate(NavFragmentDirections.actionProfileBuyerEditFragmentToProfileSellerFragment())
                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }


        binding.textViewEmail.text = "Hello! ${UserManager.user?.name}!"








        return binding.root
    }


    fun EditText.transformIntoTimePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val calendar = Calendar.getInstance()
        val timePickerOnDataSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val sdf = SimpleDateFormat(format, Locale.CHINESE)
                setText(sdf.format(calendar.time))
            }

        setOnClickListener {
            TimePickerDialog(
                context,
                timePickerOnDataSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).run {
                show()
            }
        }
    }


    fun String.toTimeLong(pattern: String = "HH:mm"): Long {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat(pattern)
        var date: Date? = Date()
        try {
            date = dateFormat.parse(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date?.time ?: 0
    }


}