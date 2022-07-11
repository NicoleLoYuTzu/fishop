package com.nicole.fishop.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import com.nicole.fishop.data.PhotoStringToUrl.bindImageWithCircleCrop
import com.nicole.fishop.databinding.FragmentProfileBuyerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities


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


        UserManager.user.let {
            bindImageWithCircleCrop(binding.imageViewMine, it?.picture)
        }

        binding.textViewName.text= UserManager.user?.name

        binding.textViewEmail.text = UserManager.user?.email

//        binding.recyclerviewFavoritesaler.adapter =
        binding.textViewFav.isInvisible




        return binding.root
    }


}