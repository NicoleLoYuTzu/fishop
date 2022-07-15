package com.nicole.fishop.profile

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentProfileSalerEditBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.StartDialogViewModel
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*


class ProfileSalerEditFragment : Fragment() {

    private val StartDialogviewModel by viewModels<StartDialogViewModel> {
        getVmFactory(
        )
    }


    private val viewModel by viewModels<ProfileSalerEditViewModel> {
        getVmFactory(
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.i("FragmentProfileSalerEditBinding onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProfileSalerEditBinding.inflate(inflater)
        binding.editTextStartTime.transformIntoTimePicker(requireContext(), "HH:mm")
        binding.editTextStopTime.transformIntoTimePicker(requireContext(), "HH:mm")


        Logger.i("FragmentProfileSalerEditBinding onCreateView")

        val businessDay: List<String> = emptyList()

        val mutableArray = businessDay.toMutableList()

        binding.textViewEmail.text =
            " 〰️〰️\uD83D\uDC20Hello! ${UserManager.user?.name}\uD83D\uDC1F 〰️〰️〰️〰️〰️〰️\uD83D\uDC20〰️〰️〰️\uD83D\uDC1F〰️〰️〰️\uD83D\uDC21〰️〰️〰️ Hello! ${UserManager.user?.name} 〰️〰️〰️\uD83D\uDC20〰️〰️〰️\uD83D\uDC1F〰️〰️〰️\uD83D\uDC21〰️〰️〰️"


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
                        val startTime = binding.editTextStartTime.text.toString()
                        val stopTime = binding.editTextStopTime.text.toString()
                        val startTimeToLong = startTime.toTimeLong()
                        val stopTimeToLong = stopTime.toTimeLong()


                        if (binding.editTextAddress.text.isEmpty()) {
                            Toast.makeText(context, "請填入地址", Toast.LENGTH_SHORT).show()
                        } else {
                            val checkADDRESS = checkAddress(binding.editTextAddress.text.toString())

                            Logger.i("checkADDRESS=> $checkADDRESS")

                            if (checkADDRESS.isNotEmpty()) {
                                UserManager.user.let {
                                    Logger.d("確定 businessday=> $mutableArray, startTimeToLong=> $startTimeToLong, stopTimeToLong $stopTimeToLong,binding.editTextShopname.text.toString()=> ${binding.editTextShopname.text.toString()}, binding.editTextPhone.text.toString(=>${binding.editTextPhone.text.toString()}")
                                    it?.businessDay = mutableArray
                                    it?.businessEndTime = stopTimeToLong.toString()
                                    it?.businessTime = startTimeToLong.toString()
                                    it?.name = binding.editTextShopname.text.toString()
                                    it?.phone = binding.editTextPhone.text.toString()
                                    it?.address = binding.editTextAddress.text.toString()
                                    it?.picture = it?.picture
                                    Logger.i("buttonSave UserManager it $it")
                                    //防呆機制
                                    if (startTime == "" || stopTime == "" || it?.name == "" || it?.phone == "" || it?.address == "" || (it?.businessDay as MutableList<String>).isEmpty()) {
                                        Toast.makeText(activity, "請把資料填完整", Toast.LENGTH_SHORT)
                                            .show()
                                    } else
                                        if (it != null) {
                                            viewModel.setSalerInfo(it)
                                            Logger.i(" viewModel.setSalerInfo it $it")
                                        }


                                    dialog.dismiss()
                                    viewModel.getOk.observe(
                                        viewLifecycleOwner, androidx.lifecycle.Observer {
                                            findNavController().navigate(NavFragmentDirections.actionProfileSalerEditFragmentToProfileSellerFragment())
                                        }
                                    )
                                }
                            } else {
                                Toast.makeText(activity, "地址找不到, 請重新輸入", Toast.LENGTH_SHORT).show()
                            }
                        }
//                        } else {
//
//                        }


                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

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

    fun checkAddress(address: String): List<Address> {
        val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())

        val address1: List<Address> = geoCoder!!.getFromLocationName(address, 1)
        return address1
    }

//    fun animation(){
//
//        val ani: ObjectAnimator = ObjectAnimator.ofFloat(_buttonC, "Y", 2000)
//        ani.duration = 4000
//        ani.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationStart(animation: Animator) {
//                val parms2 = RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT
//                )
//                parms2.addRule(RelativeLayout.CENTER_VERTICAL)
//                parms2.addRule(RelativeLayout.CENTER_HORIZONTAL)
//                _buttonC.setLayoutParams(parms2)
//            }
//
//            override fun onAnimationEnd(animation: Animator) {}
//        })
//
//
//    }


}