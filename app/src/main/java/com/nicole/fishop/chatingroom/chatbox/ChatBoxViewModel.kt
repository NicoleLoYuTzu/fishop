package com.nicole.fishop.chatingroom.chatbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.network.LoadApiStatus
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatBoxViewModel(var argument: FishToday, private val repository: FishopRepository) :
    ViewModel() {

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

    //把店家的資訊帶進來
    private val _salerInfo = MutableLiveData<FishToday>().apply {
        value = argument
    }


    val _addChatroom = MutableLiveData<ChatRecord>().apply {
        _salerInfo.value?.let { salerFishToday ->
            Logger.d("_salerInfo.value誰先跑到 => ${_salerInfo.value} ")
            UserManager.user?.let { user ->
                Logger.d("UserManager.user....誰先跑到=> ${UserManager.user} ")

                Logger.d("user.id=> ${user.id} ")
                if (UserManager.user!!.accountType == "buyer") {
                    value =
                        UserManager.user!!.id?.let { userId ->
                            ChatRecord(
                                "",
                                emptyList(),
                                salerFishToday.ownerId,
                                userId,
                                "",
                                "",
                                "",
                                "",
                                salerFishToday.name,
                                user.name.toString(),
                                salerFishToday.ownPhoto,
                                UserManager.user!!.picture.toString()

                            )
                        }
                    Logger.d("_addChatroom value 誰先跑到=> ${value} ")
                } else if (UserManager.user!!.accountType == "saler") {
                    value =
                        UserManager.user!!.id?.let { userId ->
                            UserManager.user!!.picture?.let {
                                ChatRecord(
                                    "",
                                    emptyList(),
                                    salerFishToday.ownerId,
                                    salerFishToday.buyerId,
                                    "",
                                    "",
                                    "",
                                    "",
                                    salerFishToday.name,
                                    user.name.toString(),
                                    it, salerFishToday.ownPhoto

                                )
                            }
                        }
                    Logger.d("_addChatroom value 誰先跑到=> ${value} ")

                }

            }
        }
        Logger.d("_salerInfo=>  ${_salerInfo.value},UserManager.user=> ${UserManager.user} ")
    }


    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String?>()

    val error: MutableLiveData<String?>
        get() = _error


    private var _chatRecord = MutableLiveData<ChatRecord>()

    val chatRecord: LiveData<ChatRecord>
        get() = _chatRecord

    private var _FirstCollectionSetUp = MutableLiveData<ChatRecord>()

    val FirstCollectionSetUp: LiveData<ChatRecord>
        get() = _FirstCollectionSetUp

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

    private var _chatItem = MutableLiveData<List<ChatItem>>()

    val chatItem: LiveData<List<ChatItem>>
        get() = _chatItem

    private var _checkHasRoom = MutableLiveData<ChatRecord>()

    val checkHasRoom: LiveData<ChatRecord>
        get() = _checkHasRoom

//
//    private fun toDivideSide(chatRecord: ChatRecord): List<ChatItem> {
//        Logger.d("toDivideSide")
//
//        val myChat = mutableListOf<ChatItem>()
//
//        for (chatRecordDetail in chatRecord.chats) {
//
//            if (chatRecordDetail.id == UserManager.user?.id) {
//
//                val chatMySide = ChatItem.MySide(chatRecordDetail)
//                myChat.add(chatMySide)
//                Logger.d("chatMySide $chatMySide")
//                Logger.d("UserManager.user?.id $UserManager.user?.id")
//            }
//
//
//            if (chatRecordDetail.id == _salerInfo.value?.ownerId) {
//                val chatTheOtherSide = ChatItem.TheOtherSide(chatRecordDetail)
//                myChat.add(chatTheOtherSide)
//                Logger.d("chatTheOtherSide $chatTheOtherSide")
//                Logger.d("UserManager.user?.id $UserManager.user?.id")
//            }
//        }
//        Logger.d("myChat $myChat")
//        return myChat
//    }


    fun toDivide(chat: List<ChatBoxRecord>): List<ChatItem> {
        Logger.d("toDivideSide")

        val myChat = mutableListOf<ChatItem>()
        Logger.d("chat.value ${chat}")
        for (chatRecordDetail in chat) {

            if (UserManager.user?.accountType == "buyer") {
                if (chatRecordDetail.sender == UserManager.user?.id) {

                    val chatMySide = ChatItem.MySide(chatRecordDetail)
                    myChat.add(chatMySide)
                    Logger.d("chatMySide $chatMySide")
                    Logger.d("UserManager.user?.id $UserManager.user?.id")
                }


                if (chatRecordDetail.sender == _salerInfo.value?.ownerId) {
                    val chatTheOtherSide = ChatItem.TheOtherSide(chatRecordDetail)
                    myChat.add(chatTheOtherSide)
                    Logger.d("chatTheOtherSide $chatTheOtherSide")
                    Logger.d("UserManager.user?.id $UserManager.user?.id")
                }
            }
        }

        for (chatRecordDetail in chat) {
            if (UserManager.user?.accountType == "saler") {
                if (chatRecordDetail.sender == _addChatroom.value?.saler) {

                    val chatMySide = ChatItem.MySide(chatRecordDetail)
                    myChat.add(chatMySide)
                    Logger.d("chatMySide $chatMySide")
                    Logger.d("UserManager.user?.id $UserManager.user?.id")
                }


                if (chatRecordDetail.sender == _addChatroom.value?.buyer) {
                    val chatTheOtherSide = ChatItem.TheOtherSide(chatRecordDetail)
                    myChat.add(chatTheOtherSide)
                    Logger.d("chatTheOtherSide $chatTheOtherSide")
                    Logger.d("UserManager.user?.id $UserManager.user?.id")
                }
            }
        }



        Logger.d("myChat $myChat")
        return myChat
    }


    init {
        //去拿聊天紀錄, 放入買賣家資訊用id query
//        if (UserManager.user?.accountType == "buyer") {
//            UserManager.user?.let { getBuyerChatBoxRecordResult(argument, it) }
//        }else if (UserManager.user?.accountType == "saler"){
//            UserManager.user?.let { getSalerChatBoxRecordResult(argument, it) }
//        }

        //先檢查是否有房間

//        if (UserManager.user?.accountType == "buyer") {
//            _salerInfo.value?.let {  }
//
//        }else if (UserManager.user?.accountType == "saler"){
//            UserManager.user?.let { getSalerChatBoxRecordResult(argument, it) }
//        }
        if (UserManager.user?.accountType == "buyer") {
            checkHasRoom()
        } else if (UserManager.user?.accountType == "saler") {
            _addChatroom.value.let {
                it?.buyer?.let { it1 -> checkHasRoom(it1, it.saler) }
            }
        }


        Logger.d("UserManager.user? ${UserManager.user}")

    }

    fun checkHasRoom(buyer: String, saler: String) {
        coroutineScope.launch {
            Logger.d("checkHasRoom")

            _status.value = LoadApiStatus.LOADING

            val result = repository.checkHasRoom(saler, buyer)
            Logger.d("repository.checkHasRoom")
            Logger.d("result $result")

            _checkHasRoom.value = when (result) {
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


    fun checkHasRoom() {
        coroutineScope.launch {
            Logger.d("checkHasRoom")

            _status.value = LoadApiStatus.LOADING

            val result = _salerInfo.value?.let {
                UserManager.user?.id?.let { it1 ->
                    repository.checkHasRoom(
                        it.ownerId,
                        it1
                    )
                }
            }
            Logger.d("repository.checkHasRoom")
            Logger.d("result $result")

            _checkHasRoom.value = when (result) {
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

    var liveChatItem = MutableLiveData<List<ChatBoxRecord>>()
    var observeChatItem = MutableLiveData<Boolean>()

    fun getLiveChat(roomId: String) {

        liveChatItem = repository.getLiveChat(roomId)
        Logger.d(" liveChatItem ${liveChatItem.value}")
        observeChatItem.value = true
        _status.value = LoadApiStatus.DONE
    }

    //拿紀錄是query買賣家的id
    fun getBuyerChatBoxRecordResult(salerFishToday: FishToday, user: Users) {
        coroutineScope.launch {
            Logger.d("getChatBoxRecordResult")
            Logger.d("getChatBoxRecordResult salerFishToday => $salerFishToday user=> $user ")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getChatBoxRecord(salerFishToday, user)
            Logger.d("repository.getChatBoxRecordResult()")
            Logger.d("result $result")

            _chatItem.value = when (result) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    toDivide(result.data)
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

    fun getSalerChatBoxRecordResult(salerFishToday: FishToday, user: Users) {
        coroutineScope.launch {
            Logger.d("getChatBoxRecordResult")
            Logger.d("getChatBoxRecordResult salerFishToday => $salerFishToday user=> $user ")

            _status.value = LoadApiStatus.LOADING

            val result = repository.getChatBoxRecord(salerFishToday, user)
            Logger.d("repository.getChatBoxRecordResult()")
            Logger.d("result $result")

            _chatItem.value = when (result) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    toDivide(result.data)
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


    fun addChatroom() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            //如果沒有聊天紀錄就建立一個新的chatroom
            Logger.d("_addChatroom.value誰先跑到 ${_addChatroom.value}")
            Logger.d("repository.addChatroom()")

            _chatRecord.value = when (val result = repository.addChatroom(_addChatroom.value!!)) {
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


    fun sendChat(chatRoomId: String, chatBoxRecord: ChatBoxRecord) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            Logger.d("repository.sendChat()")

            when (val result = repository.sendChat(chatRoomId, chatBoxRecord)) {
                is Result1.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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
            _refreshStatus.value = false
        }

    }


    fun sendLastChat(chatRoomId: String, chatRecord: ChatRecord) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            Logger.d("repository.sendChat()")

            _FirstCollectionSetUp.value =
                when (val result = repository.sendLastChat(chatRoomId, chatRecord)) {
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
                        _error.value =
                            FishopApplication.instance.getString(R.string.you_know_nothing)
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            _refreshStatus.value = false
        }

    }
}