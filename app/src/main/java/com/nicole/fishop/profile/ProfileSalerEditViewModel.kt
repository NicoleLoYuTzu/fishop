package com.nicole.fishop.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileSalerEditViewModel(private val repository: FishopRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status


    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _users = MutableLiveData<Users>()

    val users: LiveData<Users>
        get() = _users



//    init {
//        getSalerInfo()
//    }
//
//    fun getSalerInfo(users: Users){
//        coroutineScope.launch {
//            Logger.d("getSalerInfo")
//            _status.value = LoadApiStatus.LOADING
//            Logger.d("users => $users")
//            when (val result = repository.getSalerInfo(users)) {
//                is Result1.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    Logger.d("success")
//                }
//                is Result1.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result1.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }



}