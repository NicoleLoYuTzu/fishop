package com.nicole.fishop.data.source.remote

import android.util.Log.d
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.fishop.data.FishCategory
import com.nicole.fishop.data.FishRecord
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.source.FishopDataSource
import com.nicole.fishop.data.Users
import com.nicole.fishop.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FishopRemoteDataSource: FishopDataSource {

    private const val PATH_EVERYDAYFISHES = "EverdayFishes"
    private const val KEY_CREATED_TIME = "time"


    override suspend fun getUsersInfo(): Result<Users> {
        TODO("Not yet implemented")
    }

    override suspend fun getFishRecord(): Result<List<FishRecord>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_EVERYDAYFISHES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<FishRecord>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val article = document.toObject(Article::class.java)
                        list.add(article)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PublisherApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun getFishCategory(): Result<FishCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getChatRecord(): Result<ChatRecord> {
        TODO("Not yet implemented")
    }
}