package com.nicole.fishop.data.source

import com.nicole.fishop.data.*

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
}