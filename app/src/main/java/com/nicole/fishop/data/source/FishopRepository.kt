package com.nicole.fishop.data.source

import androidx.lifecycle.MutableLiveData
import com.nicole.fishop.MainViewModel
import com.nicole.fishop.data.*
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger

interface FishopRepository {

    suspend fun getUsersInfo(): Result1<Users>

    suspend fun getFishRecord(User: Users): Result1<List<FishRecord>>

    suspend fun getFishAll(): Result1<List<Category>>

    suspend fun getFishTodayAll(): Result1<List<FishToday>>

    suspend fun getFishTodayFilterAll(fish:String): Result1<List<FishToday>>

    suspend fun getChatRecord():Result1<ChatRecord>

    suspend fun getGoogleMap(location: String):Result1<SellerLocation>

    suspend fun getAllSellerAddressResult(salerId: List<String>):Result1<List<SellerLocation>>

    suspend fun setTodayFishRecord(fishToday: FishToday,Categories: List<FishTodayCategory>,User: Users): Result1<Boolean>

//    suspend fun setUserAcountType(users: Users,viewModel: MainViewModel): Result1<Boolean>

    suspend fun userSignIn(users: Users): Result1<Users>

    suspend fun getSalerInfo(users: Users): Result1<Users>

    suspend fun setSalerInfo(users: Users):  Result1<Boolean>

    suspend fun getChatBoxRecord(salerFishToday: FishToday,user: Users):  Result1<List<ChatBoxRecord>>

    suspend fun getSalerChatRecordResult(user: Users):  Result1<List<ChatRecord>>

    suspend fun getBuyerChatRecordResult(user: Users):  Result1<List<ChatRecord>>

    suspend fun addChatroom(chat: ChatRecord):  Result1<ChatRecord>

    suspend fun sendChat(chatRoomId: String,chat: ChatBoxRecord):  Result1<Boolean>

    suspend fun sendLastChat(chatRoomId: String,chat: ChatRecord):  Result1<ChatRecord>

    suspend fun checkHasRoom(salerId: String,userId: String):  Result1<ChatRecord>

    fun getLiveChat(chatRoomId: String): MutableLiveData<List<ChatBoxRecord>>









}