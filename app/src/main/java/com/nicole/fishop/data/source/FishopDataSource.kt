package com.nicole.fishop.data.source

import com.nicole.fishop.data.*

interface FishopDataSource {

    suspend fun getUsersInfo(): Result1<Users>

    suspend fun getFishRecord(): Result1<List<FishRecord>>

    suspend fun getFishAll(): Result1<List<Category>>

    suspend fun getChatRecord():Result1<ChatRecord>
}