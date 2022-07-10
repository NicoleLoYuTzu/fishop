package com.nicole.fishop.fishSeller

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayBinding
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayItemItemBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
import java.util.*


class AddTodayCategoryFragment : Fragment() {

    private val viewModel by viewModels<AddTodayCategoryViewModel> {
        getVmFactory(
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFishSellerAddTodayBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.recyclerView.adapter = AddTodayCategoryItemAdapter(viewModel)

//        viewModel.fishtodayItem.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            (binding.recyclerView.adapter as AddTodayCategoryItemAdapter).submitList(a)
////            binding.recyclerView.adapter = AddTodayCategoryItemAdapter()
//        })

        viewModel.fishAll.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            (binding.recyclerView.adapter as AddTodayCategoryItemAdapter).submitList(it)
        })

        binding.textViewToday.text = getNow()
        binding.buttonSave.setOnClickListener {
            if (viewModel.fishTodayCategories.isEmpty()) {
                AlertDialog.Builder(context)
                    .setTitle("您沒有填入任何資料, 是否忘記勾選?")
                    .setPositiveButton("返回") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                AlertDialog.Builder(context)
                    .setTitle("確定儲存?")
                    .setPositiveButton("確定") { dialog, _ ->
                        viewModel.fishToday.name = UserManager.user?.name.toString()
                        viewModel.fishToday.ownPhoto = UserManager.user?.picture.toString()
                        UserManager.user?.let { it1 ->
                            viewModel.setTodayFishRecord(
                                viewModel.fishToday,
                                viewModel.fishTodayCategories,
                                it1
                            )
                        }

                        Logger.d("viewModel.fishTodayCategories ${viewModel.fishTodayCategories}")
                        Toast.makeText(context, "已儲存", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        findNavController().navigate(NavFragmentDirections.actionFishSellerFragmentAddTodayToFishSellerFragment())
                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        binding.buttonBack.setOnClickListener {
            if (viewModel.fishTodayCategories.isNotEmpty()) {
                AlertDialog.Builder(context)
                    .setTitle("資料尚未儲存, 確定要返回? (若返回則資料將遺失)")
                    .setPositiveButton("確定返回") { dialog, _ ->
                        dialog.dismiss()
                        findNavController().navigate(NavFragmentDirections.actionFishSellerFragmentAddTodayToFishSellerFragment())
                    }
                    .setNeutralButton("取消") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {

                findNavController().popBackStack()
            }
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