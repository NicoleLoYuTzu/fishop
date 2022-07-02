package com.nicole.fishop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.data.Users
import com.nicole.fishop.databinding.ActivityStartDialogBinding
import com.nicole.fishop.ext.getVmFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class StartDialog() : AppCompatDialogFragment() {
//    var preferences: SharedPreferences? = null
    private lateinit var binding: ActivityStartDialogBinding

    private val viewModel by viewModels<StartDialogViewModel> {
        getVmFactory(
        )
    }

    private val mainViewModel by viewModels<MainViewModel> {
        getVmFactory(
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ReviewDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        preferences = this.requireActivity()
//            .getSharedPreferences("pref", Context.MODE_PRIVATE)



        binding = ActivityStartDialogBinding.inflate(inflater, container, false)


        binding.constraint.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_up
            )
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.checkBoxBuyer.setOnCheckedChangeListener { compoundButton, b ->

            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("確定成為買家?")
                    .setPositiveButton("確定") { dialog, _ ->
                        val user = Users("", "")
                        user.accountType = "buyer"
                        viewModel.setUserAcountType(user,mainViewModel)
                        dialog.dismiss()
//                        findNavController().navigate(NavFragmentDirections.actionStartDialogToFishBuyerFragment())
                        findNavController().navigate(NavFragmentDirections.actionStartDialogToMainActivity())
                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.checkBoxSaler.setOnCheckedChangeListener { compoundButton, b ->

            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("確定成為賣家?")
                    .setPositiveButton("確定") { dialog, _ ->
                        val user = Users()
                        user.accountType = "saler"
//                        viewModel.setUserAcountType(user)
                        dialog.dismiss()
                        findNavController().navigate(NavFragmentDirections.actionStartDialogToFishSellerFragment())
                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }



        return binding.root
    }


    override fun dismiss() {
        binding.constraint.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_down
            )
        )
        lifecycleScope.launch {
            delay(200)
            super.dismiss()
        }
    }
}