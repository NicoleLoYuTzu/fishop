package com.nicole.fishop.data.source.remote

import android.util.Log.d
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopDataSource
import com.nicole.fishop.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FishopRemoteDataSource: FishopDataSource {

    private const val PATH_EVERYDAYFISHES = "EverdayFishes"
    private const val KEY_CREATED_TIME = "time"


    override suspend fun getUsersInfo(): Result1<Users> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishRecord(): Result1<List<FishRecord>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_EVERYDAYFISHES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<FishRecord>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val fishRecord = document.toObject(FishRecord::class.java)
                        list.add(fishRecord)
                    }
                    continuation.resume(Result1.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result1.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result1.Fail(FishopApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getFishCategory(): Result1<FishCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getChatRecord(): Result1<ChatRecord> {
        TODO("Not yet implemented")
    }
}