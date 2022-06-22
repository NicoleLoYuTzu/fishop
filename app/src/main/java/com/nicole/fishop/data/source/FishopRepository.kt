package com.nicole.fishop.data.source

import com.nicole.fishop.data.*
import com.nicole.fishop.util.Logger

interface FishopRepository {

    suspend fun getUsersInfo(): Result1<Users>

    suspend fun getFishRecord(): Result1<List<FishRecord>>

    suspend fun getFishAll(): Result1<List<Category>>

    suspend fun getFishTodayAll(): Result1<List<FishToday>>

    suspend fun getChatRecord():Result1<ChatRecord>

}