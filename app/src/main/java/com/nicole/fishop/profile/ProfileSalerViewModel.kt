package com.nicole.fishop.profile

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.Result1
import com.nicole.fishop.data.Users
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import java.util.logging.Handler

class ProfileSalerViewModel(private val repository: FishopRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status


    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _error = MutableLiveData<String?>()

    val error: MutableLiveData<String?>
        get() = _error


//    var userManager = MutableLiveData<UserManager>()

    private val _users = MutableLiveData<Users>()

    val users: LiveData<Users>
        get() = _users


    init {
//        userManager.value?.user?.let { getSalerInfo(it) }
        UserManager.user?.let { getSalerInfo(it) }
        Logger.d("UserManager.user ${UserManager.user}")
        Logger.d("delay")

        Logger.d("init")
    }

    fun getSalerInfo(users: Users) {
        coroutineScope.launch {
            Logger.d("getSalerInfo")
            _status.value = LoadApiStatus.LOADING
            Logger.d("users => $users")

            _users.value = when (val result = repository.getSalerInfo(users)) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Logger.d("success")
                    result.data
                }
                is Result1.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result1.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

}