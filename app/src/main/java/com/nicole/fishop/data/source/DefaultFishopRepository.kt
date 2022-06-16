package com.nicole.fishop.data.source

import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.FishCategory
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.Users

class DefaultFishopRepository (private val remoteDataSource: FishopDataSource,
                               private val localDataSource: FishopDataSource):FishopRepository{
    override suspend fun getUsersInfo(): Result<Users> {
        return remoteDataSource.getUsersInfo()
    }

    override suspend fun getFishRecord(): Result<FishRecord> {
        return remoteDataSource.getFishRecord()
    }

    override suspend fun getFishCategory(): Result<FishCategory> {
        return remoteDataSource.getFishCategory()
    }

    override suspend fun getChatRecord(): Result<ChatRecord> {
        return remoteDataSource.getChatRecord()
    }
}