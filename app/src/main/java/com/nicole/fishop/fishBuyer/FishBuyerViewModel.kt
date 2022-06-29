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

class FishBuyerViewModel(private val repository: FishopRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

//    private val _product = MutableLiveData<Product>().apply {
//        value = arguments
//    }
//
//    val product: LiveData<Product>
//        get() = _product


    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error


    private var _fishToday = MutableLiveData<List<FishToday>>()

    val fishToday: LiveData<List<FishToday>>
        get() = _fishToday


//    var fishAddress = FishToday()



    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToGoogleMap = MutableLiveData<FishToday>()

    val navigateToGoogleMap: LiveData<FishToday>
        get() = _navigateToGoogleMap

    fun navigateToGoogleMap(fishToday: FishToday) {
        _navigateToGoogleMap.value = fishToday
    }

    fun onGoogleMapNavigated() {
        _navigateToGoogleMap.value = null
    }

    init {
        getFishTodayAllResult()
    }

    fun getFishTodayAllResult() {
        coroutineScope.launch {
            Logger.d("getFishTodayAllResult")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getFishTodayAll()
            Logger.d("repository.getFishTodayAll()")
            Logger.d("result $result")

            _fishToday.value = when (result) {
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

    fun getFishTodayFilterResult(fish: String) {
        coroutineScope.launch {
            Logger.d("getFishTodayFilterResult")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getFishTodayFilterAll(fish)
            Logger.d("repository.getFishTodayFilterAll()")
            Logger.d("getFishTodayFilterAll result $result")

            _fishToday.value = when (result) {
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

    private var _sellerLocations = MutableLiveData<List<SellerLocation>>()

    val sellerLocations: LiveData<List<SellerLocation>>
        get() = _sellerLocations



    fun getAllSellerAddressResult(ownerIds: List<String>) {
        coroutineScope.launch {
            Logger.d("getGoogleMapResult")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getAllSellerAddressResult(ownerIds)
            Logger.d("repository.getGoogleMap()")
            Logger.d("getGoogleMap result $result")

            _sellerLocations.value = when (result) {
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

//    private var _getDistance = MutableLiveData<List<SellerLocation>>()
//
//    val getDistance: LiveData<List<SellerLocation>>
//        get() = _getDistance
//    fun getDistanceResult(location: String) {
//        coroutineScope.launch {
//            Logger.d("getGoogleMapResult")
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = repository.getDistanceResult(location)
//            Logger.d("repository.getGoogleMap()")
//            Logger.d("getGoogleMap result $result")
//
//            _getDistance.value = when (result) {
//                is Result1.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//                }
//                is Result1.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result1.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = FishopApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//            _refreshStatus.value = false
//
//        }
//    }
}

