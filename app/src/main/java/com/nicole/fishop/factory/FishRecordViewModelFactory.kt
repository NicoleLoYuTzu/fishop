package com.nicole.fishop.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.fishBuyer.FishBuyerViewModel
import com.nicole.fishop.fishSeller.FishSellerViewModel

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class FishRecordViewModelFactory constructor(
    private val repository: FishopRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(FishSellerViewModel::class.java) ->
                    FishSellerViewModel(repository)

                isAssignableFrom(FishBuyerViewModel::class.java) ->
                    FishBuyerViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
