package com.nicole.fishop.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.fishop.data.source.FishopRepository

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val stylishRepository: FishopRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
//                isAssignableFrom(MainViewModel::class.java) ->
//                    MainViewModel(stylishRepository)
//
//                isAssignableFrom(HomeViewModel::class.java) ->
//                    HomeViewModel(stylishRepository)
//
//                isAssignableFrom(CartViewModel::class.java) ->
//                    CartViewModel(stylishRepository)
//
//                isAssignableFrom(PaymentViewModel::class.java) ->
//                    PaymentViewModel(stylishRepository)
//
//                isAssignableFrom(LoginViewModel::class.java) ->
//                    LoginViewModel(stylishRepository)
//
//                isAssignableFrom(CheckoutSuccessViewModel::class.java) ->
//                    CheckoutSuccessViewModel(stylishRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
