package com.nicole.fishop.data.source.local

import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopDataSource

class FishopLocalDataSource: FishopDataSource {
    override suspend fun getUsersInfo(): Result1<Users> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishRecord(): Result1<List<FishRecord>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishCategory(): Result1<FishCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getChatRecord(): Result1<ChatRecord> {
        TODO("Not yet implemented")
    }
}