package com.nicole.fishop.data.source

import androidx.lifecycle.LiveData
import com.nicole.fishop.MainViewModel
import com.nicole.fishop.data.*
import com.nicole.fishop.login.UserManager

class DefaultFishopRepository (private val remoteDataSource: FishopDataSource,):FishopRepository{
    override suspend fun getUsersInfo(): Result1<Users> {
        return remoteDataSource.getUsersInfo()
    }

    override suspend fun getFishRecord(): Result1<List<FishRecord>> {
        return remoteDataSource.getFishRecord()
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

    override suspend fun getAllSellerAddressResult(ownerId: List<String>): Result1<List<SellerLocation>> {
        return remoteDataSource.getAllSellerAddressResult(ownerId)
    }

    override suspend fun setTodayFishRecord(fishToday: FishToday,Categories: List<FishTodayCategory>): Result1<Boolean> {
        return remoteDataSource.setTodayFishRecord(fishToday,Categories)
    }

//    override suspend fun setUserAcountType(users: Users,viewModel: MainViewModel): Result1<Boolean>{
//        return remoteDataSource.setUserAcountType(users,viewModel)
//    }

    override suspend fun userSignIn(users: Users): Result1<Users> {
        return remoteDataSource.userSignIn(users)
    }

    override suspend fun getSalerInfo(users: Users): Result1<Users>{
        return remoteDataSource.getSalerInfo(users)
    }

    override suspend fun setSalerInfo(users: Users):  Result1<Boolean>{
        return remoteDataSource.setSalerInfo(users)
    }
}