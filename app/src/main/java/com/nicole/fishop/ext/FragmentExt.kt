package com.nicole.fishop.ext

import androidx.fragment.app.Fragment
import com.nicole.fishop.factory.ViewModelFactory
import com.nicole.fishop.FishopApplication

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as FishopApplication).repository
    return ViewModelFactory(repository)
}