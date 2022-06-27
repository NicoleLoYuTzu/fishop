package com.nicole.fishop.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.fishBuyer.FishBuyerGoogleMapViewModel
import com.nicole.fishop.fishBuyer.FishBuyerViewModel
import com.nicole.fishop.fishSeller.FishSellerViewModel
import com.nicole.fishop.fishSeller.AddTodayCategoryViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val fishopRepository: FishopRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(FishSellerViewModel::class.java) ->
                    FishSellerViewModel(fishopRepository)

                isAssignableFrom(FishBuyerViewModel::class.java) ->
                    FishBuyerViewModel(fishopRepository)

                isAssignableFrom(AddTodayCategoryViewModel::class.java) ->
                    AddTodayCategoryViewModel(fishopRepository)

                isAssignableFrom(FishBuyerGoogleMapViewModel::class.java) ->
                    FishBuyerGoogleMapViewModel(fishopRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
