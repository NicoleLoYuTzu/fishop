package com.nicole.fishop.data.source

import com.nicole.fishop.MainViewModel
import com.nicole.fishop.data.*
import com.nicole.fishop.login.UserManager

interface FishopDataSource {

    suspend fun getUsersInfo(): Result1<Users>

    suspend fun getFishRecord(): Result1<List<FishRecord>>

    suspend fun getFishAll(): Result1<List<Category>>

    suspend fun getFishTodayAll(): Result1<List<FishToday>>

    suspend fun getFishTodayFilterAll(fish:String): Result1<List<FishToday>>

    suspend fun getChatRecord():Result1<ChatRecord>

    suspend fun getGoogleMap(location: String):Result1<SellerLocation>

    suspend fun getAllSellerAddressResult(ownerId: List<String>):Result1<List<SellerLocation>>

    suspend  fun setTodayFishRecord(fishToday: FishToday,Categories: List<FishTodayCategory>): Result1<Boolean>

//    suspend fun setUserAcountType(users: Users,viewModel: MainViewModel): Result1<Boolean>

    suspend fun userSignIn(users: Users): Result1<Boolean>

    suspend fun getSalerInfo(users: Users): Result1<Users>
}