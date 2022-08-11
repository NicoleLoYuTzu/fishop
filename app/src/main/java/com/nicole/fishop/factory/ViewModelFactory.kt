package com.nicole.fishop.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.fishop.MainViewModel
import com.nicole.fishop.chatingroom.chat.ChatViewModel
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.fishbuyer.FishBuyerGoogleMapViewModel
import com.nicole.fishop.fishbuyer.FishBuyerViewModel
import com.nicole.fishop.fishseller.AddTodayCategoryViewModel
import com.nicole.fishop.fishseller.FishSellerViewModel
import com.nicole.fishop.login.StartDialogViewModel
import com.nicole.fishop.profile.ProfileSalerEditViewModel
import com.nicole.fishop.profile.ProfileSalerViewModel

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

                isAssignableFrom(StartDialogViewModel::class.java) ->
                    StartDialogViewModel(fishopRepository)

                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(fishopRepository)

                isAssignableFrom(ProfileSalerViewModel::class.java) ->
                    ProfileSalerViewModel(fishopRepository)

                isAssignableFrom(ProfileSalerEditViewModel::class.java) ->
                    ProfileSalerEditViewModel(fishopRepository)

                isAssignableFrom(ChatViewModel::class.java) ->
                    ChatViewModel(fishopRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
