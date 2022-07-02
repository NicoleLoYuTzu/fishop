package com.nicole.fishop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.internal.AccountType
import com.google.android.gms.maps.model.LatLng
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.data.Result1
import com.nicole.fishop.data.Users
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StartDialogViewModel(private val repository: FishopRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error


    val startLocation = MutableLiveData<LatLng>()


    var _fishToday = MutableLiveData<List<FishToday>>()

    val fishToday: LiveData<List<FishToday>>
        get() = _fishToday


//    var fishAddress = FishToday()


    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    private val _navigateToGoogleMap = MutableLiveData<FishToday>()
//
//    val navigateToGoogleMap: LiveData<FishToday>
//        get() = _navigateToGoogleMap
//
//    fun navigateToGoogleMap(fishToday: FishToday) {
//        _navigateToGoogleMap.value = fishToday
//    }
//
//    fun onGoogleMapNavigated() {
//        _navigateToGoogleMap.value = null
//    }


    fun setUserAcountType(accountType: Users,viewModel: MainViewModel) {
        coroutineScope.launch {
            Logger.d("setUserAcountType")

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.setUserAcountType(accountType,viewModel)) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Logger.d("success")
                }
                is Result1.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result1.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}
