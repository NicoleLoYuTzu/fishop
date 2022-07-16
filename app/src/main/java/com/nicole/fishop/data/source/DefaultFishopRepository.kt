package com.nicole.fishop.data.source

import androidx.lifecycle.MutableLiveData
import com.nicole.fishop.data.*

class DefaultFishopRepository (private val remoteDataSource: FishopDataSource,):FishopRepository{
    override suspend fun getUsersInfo(): Result1<Users> {
        return remoteDataSource.getUsersInfo()
    }

    override suspend fun getFishRecord(User: Users): Result1<List<FishRecord>> {
        return remoteDataSource.getFishRecord(User)
    }

    override suspend fun getFishAll(): Result1<List<Category>> {
        return remoteDataSource.getFishAll()
    }

    override suspend fun getFishTodayAll(): Result1<List<FishToday>> {
        return remoteDataSource.getFishTodayAll()
    }

    override suspend fun getFishTodayFilterAll(fish:String): Result1<List<FishToday>>{
        return remoteDataSource.getFishTodayFilterAll(fish)
    }

    override suspend fun getChatRecord(): Result1<ChatRecord> {
        return remoteDataSource.getChatRecord()
    }

    override suspend fun getGoogleMap(location: String): Result1<SellerLocation> {
        return remoteDataSource.getGoogleMap(location)
    }

    override suspend fun getAllSellerAddressResult(salerId: List<String>): Result1<List<SellerLocation>> {
        return remoteDataSource.getAllSellerAddressResult(salerId)
    }

    override suspend fun setTodayFishRecord(fishToday: FishToday,Categories: List<FishTodayCategory>,User: Users): Result1<Boolean> {
        return remoteDataSource.setTodayFishRecord(fishToday,Categories,User)
    }

//    override suspend fun setUserAcountType(users: Users,viewModel: MainViewModel): Result1<Boolean>{
//        return remoteDataSource.setUserAcountType(users,viewModel)
//    }

    override suspend fun checkBuyerAccount(accountType: String,email: String): Result1<Users>{
        return remoteDataSource.checkBuyerAccount(accountType,email)
    }

    override suspend fun checkSalerAccount(accountType: String,email: String): Result1<Users>{
        return remoteDataSource.checkSalerAccount(accountType,email)
    }

    override suspend fun userSignIn(users: Users): Result1<Users> {
        return remoteDataSource.userSignIn(users)
    }

    override suspend fun getSalerInfo(users: Users): Result1<Users>{
        return remoteDataSource.getSalerInfo(users)
    }

    override suspend fun setSalerInfo(users: Users):  Result1<Boolean>{
        return remoteDataSource.setSalerInfo(users)
    }

    override suspend fun getChatBoxRecord(salerFishToday: FishToday,user: Users): Result1<List<ChatBoxRecord>>{
        return remoteDataSource.getChatBoxRecord(salerFishToday, user)
    }

    override suspend fun getSalerChatRecordResult(user: Users):  Result1<List<ChatRecord>>{
        return remoteDataSource.getSalerChatRecordResult(user)
    }

    override suspend fun getSalerSnapShotChatRecordResult(user: Users): Result1<List<ChatRecord>>{
        return remoteDataSource.getBuyerChatRecordResult(user)
    }

    override suspend fun getBuyerChatRecordResult(user: Users):  Result1<List<ChatRecord>>{
        return remoteDataSource.getBuyerChatRecordResult(user)
    }

    override suspend fun getBuyerSnapShotChatRecordResult(user: Users): Result1<List<ChatRecord>>{
        return remoteDataSource.getBuyerChatRecordResult(user)
    }

    override suspend fun addChatroom(chatRecord: ChatRecord):  Result1<ChatRecord>{
        return remoteDataSource.addChatroom(chatRecord)
    }

    override suspend fun sendChat(chatRoomId: String,chat: ChatBoxRecord):  Result1<Boolean>{
        return remoteDataSource.sendChat(chatRoomId, chat)
    }

    override suspend fun sendLastChat(chatRoomId: String,chat: ChatRecord):  Result1<ChatRecord>{
        return remoteDataSource.sendLastChat(chatRoomId, chat)
    }

    override suspend fun checkHasRoom(salerId: String,userId: String):  Result1<ChatRecord>{
        return remoteDataSource.checkHasRoom(salerId, userId)
    }

    override fun getLiveChat(chatRoomId: String): MutableLiveData<List<ChatBoxRecord>> {
        return remoteDataSource.getLiveChat(chatRoomId)
    }
}