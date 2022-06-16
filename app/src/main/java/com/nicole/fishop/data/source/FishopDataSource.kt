package com.nicole.fishop.data.source

import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.FishCategory
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.Users

interface FishopDataSource {

    suspend fun getUsersInfo():Result<Users>

    suspend fun getFishRecord(): Result<List<FishRecord>>

    suspend fun getFishCategory(): Result<FishCategory>

    suspend fun getChatRecord():Result<ChatRecord>
}