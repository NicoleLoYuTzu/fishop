package com.nicole.fishop.ext

import androidx.fragment.app.Fragment
import com.nicole.fishop.factory.ViewModelFactory
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.factory.FishRecordViewModelFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): FishRecordViewModelFactory {
    val repository = (requireContext().applicationContext as FishopApplication).repository
    return FishRecordViewModelFactory(repository)
}