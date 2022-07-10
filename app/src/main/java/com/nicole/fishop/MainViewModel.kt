package com.nicole.fishop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.data.Users
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.data.source.remote.FishopRemoteDataSource
import com.nicole.fishop.util.Logger

class MainViewModel(private val fishopRepository: FishopRepository) : ViewModel() {

    private val _user = MutableLiveData<Users>()

    val user: LiveData<Users>
        get() = _user


   var newUser = MutableLiveData<Boolean>()



    fun setupUser(user: Users) {

        _user.value = user
        Logger.i("=============")
        Logger.i("| setupUser |")
        Logger.i("user=$user")
        Logger.i("MainViewModel=$this")
        Logger.i("=============")
    }

    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<Users>()

    val navigateToLoginSuccess: LiveData<Users>
        get() = _navigateToLoginSuccess

    // Handle navigation to profile by bottom nav directly which includes icon change
    private val _navigateToProfileByBottomNav = MutableLiveData<Users>()

    val navigateToProfileByBottomNav: LiveData<Users>
        get() = _navigateToProfileByBottomNav

    // Handle navigation to home by bottom nav directly which includes icon change
    private val _navigateToHomeByBottomNav = MutableLiveData<Boolean>()

    val navigateToHomeByBottomNav: LiveData<Boolean>
        get() = _navigateToHomeByBottomNav

    fun navigateToLoginSuccess(user: Users) {
        _navigateToLoginSuccess.value = user
    }

    fun onLoginSuccessNavigated() {
        _navigateToLoginSuccess.value = null
    }

    fun navigateToProfileByBottomNav(user: Users) {
        _navigateToProfileByBottomNav.value = user
    }

    fun onProfileNavigated() {
        _navigateToProfileByBottomNav.value = null
    }

    fun navigateToHomeByBottomNav() {
        _navigateToHomeByBottomNav.value = true
    }

    fun onHomeNavigated() {
        _navigateToHomeByBottomNav.value = null
    }
}
