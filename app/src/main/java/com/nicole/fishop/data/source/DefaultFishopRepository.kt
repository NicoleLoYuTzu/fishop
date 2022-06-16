package com.nicole.fishop.data.source

import com.nicole.fishop.data.*

class DefaultFishopRepository (private val remoteDataSource: FishopDataSource,):FishopRepository{
    override suspend fun getUsersInfo(): Result1<Users> {
        return remoteDataSource.getUsersInfo()
    }

    override suspend fun getFishRecord(): Result1<List<FishRecord>> {
        return remoteDataSource.getFishRecord()
    }

    override suspend fun getFishCategory(): Result1<FishCategory> {
        return remoteDataSource.getFishCategory()
    }

    override suspend fun getChatRecord(): Result1<ChatRecord> {
        return remoteDataSource.getChatRecord()
    }
}