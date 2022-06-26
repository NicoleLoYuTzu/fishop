package com.nicole.fishop.chatingroom.buyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.data.Result1
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BuyerChatViewModel (private val repository: FishopRepository) : ViewModel() {

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

//    private var _fishCategory = MutableLiveData<List<FishCategory>>()
//
//    val fishCategory: LiveData<List<FishCategory>>
//        get() = _fishCategory

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getChatRecordResult()
    }

    fun getChatRecordResult() {
//        coroutineScope.launch {
//            Logger.d("getFishTodayAllResult")
//
//            _status.value = LoadApiStatus.LOADING
//
////            val result = repository.getChatRecordResult()
//            Logger.d("repository.getFishTodayAll()")
//            Logger.d("result $result")
//
//            _fishToday.value = when (result) {
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
//        }
    }


}