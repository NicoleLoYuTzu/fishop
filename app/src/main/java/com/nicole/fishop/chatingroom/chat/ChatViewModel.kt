package com.nicole.fishop.chatingroom.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.FishToday
import com.nicole.fishop.data.Result1
import com.nicole.fishop.data.Users
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel (private val repository: FishopRepository) : ViewModel() {

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


    private var _chatRecord = MutableLiveData<List<ChatRecord>>()

    val chatRecord: LiveData<List<ChatRecord>>
        get() = _chatRecord


    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        if(UserManager.user?.accountType == "saler"){
            getSalerChatRecordResult()
        }else{
            getBuyerChatRecordResult()
        }

    }


    fun getSalerChatRecordResult() {
        coroutineScope.launch {
            Logger.d("getChatRecordResult")

            _status.value = LoadApiStatus.LOADING

            val result = UserManager.user?.let { repository.getSalerChatRecordResult(it)}
            Logger.d("repository.getSalerChatRecordResult()")
            Logger.d("result $result")

            _chatRecord.value = when (result) {
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

    fun getBuyerChatRecordResult() {
        coroutineScope.launch {
            Logger.d("getChatRecordResult")

            _status.value = LoadApiStatus.LOADING

            val result = UserManager.user?.let { repository.getBuyerChatRecordResult(it) }
            Logger.d("repository.getBuyerChatRecordResult()")
            Logger.d("result $result")

            _chatRecord.value = when (result) {
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