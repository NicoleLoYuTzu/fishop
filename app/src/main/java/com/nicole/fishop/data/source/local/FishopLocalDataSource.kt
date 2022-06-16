package com.nicole.fishop.data.source.local

import com.nicole.fishop.data.FishCategory
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.source.FishopDataSource
import com.nicole.fishop.data.Users

class FishopLocalDataSource: FishopDataSource {
    override suspend fun getUsersInfo(): Result<Users> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishRecord(): Result<FishRecord> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishCategory(): Result<FishCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getChatRecord(): Result<ChatRecord> {
        TODO("Not yet implemented")
    }
}