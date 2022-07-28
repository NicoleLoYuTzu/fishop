package com.nicole.fishop.ext

import androidx.fragment.app.Fragment
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.factory.ChatBoxViewModelFactory
import com.nicole.fishop.factory.ViewModelFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as FishopApplication).repository
    return ViewModelFactory(repository)
}
// fun Fragment.getVmFactory(addressKey: FishToday): ViewModelFactory {
//    val repository = (requireContext().applicationContext as FishopApplication).repository
//    return ViewModelFactory(repository)
// }

fun Fragment.getVmFactory(fishToday: FishToday): ChatBoxViewModelFactory {
    val repository = (requireContext().applicationContext as FishopApplication).repository
    return ChatBoxViewModelFactory(fishToday, repository)
}
