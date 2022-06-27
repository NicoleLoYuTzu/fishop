package com.nicole.fishop.fishBuyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import com.nicole.fishop.util.ServiceLocator.repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FishBuyerGoogleMapViewModel(private val repository: FishopRepository) : ViewModel() {

    var fishToday: FishToday? = null

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToGoogleMap = MutableLiveData<FishToday>()

    val navigateToGoogleMap: LiveData<FishToday>
        get() = _navigateToGoogleMap

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private var _sellerLocation = MutableLiveData<SellerLocation>()

    val sellerLocation: LiveData<SellerLocation>
        get() = _sellerLocation

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    fun getGoogleMapResult(location: String) {
        coroutineScope.launch {
            Logger.d("getGoogleMapResult")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getGoogleMap(location)
            Logger.d("repository.getGoogleMap()")
            Logger.d("getGoogleMap result $result")

            _sellerLocation.value = when (result) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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
            _refreshStatus.value = false

        }
    }
}