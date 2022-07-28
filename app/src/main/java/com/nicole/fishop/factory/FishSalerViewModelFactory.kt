package com.nicole.fishop.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nicole.fishop.chatingroom.chatbox.ChatBoxViewModel
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.data.source.FishopRepository

class ChatBoxViewModelFactory constructor(
    private val fishToday: FishToday,
    private val fishopRepository: FishopRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ChatBoxViewModel::class.java) ->
                    ChatBoxViewModel(fishToday, fishopRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
