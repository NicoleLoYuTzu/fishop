package com.nicole.fishop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.data.Users
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.util.Logger

class MainViewModel(private val fishopRepository: FishopRepository) : ViewModel() {

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users>
        get() = _user

    var newUser = MutableLiveData<Boolean>()


}
