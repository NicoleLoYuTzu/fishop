package com.nicole.fishop.data.source.remote

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.util.Log.d
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopDataSource
import com.nicole.fishop.util.Logger
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FishopRemoteDataSource : FishopDataSource {

    override suspend fun getUsersInfo(): Result1<Users> {
        TODO("Not yet implemented")
    }

//    @SuppressLint("SimpleDateFormat")
//    @RequiresApi(Build.VERSION_CODES.O)
//    override suspend fun getFishRecord(): Result1<List<FishRecord>> =
//        suspendCoroutine { continuation ->
//            FirebaseFirestore.getInstance()
//                .collection(PATH_EVERYDAYFISHES)
//                .get()
//                .addOnCompleteListener { everydayFishes ->
//                    if (everydayFishes.isSuccessful) {
//                        val list1 = mutableListOf<FishRecord>()
//                        var count = everydayFishes.result.size()
//                        for (document1 in everydayFishes.result!!) {
////                            Logger.d("document1 count => $count")
//                            Logger.d("document1.data => ${document1.data}")
//                            val fishRecord = document1.toObject(FishRecord::class.java)
//                            FirebaseFirestore.getInstance()
//                                .collection(PATH_EVERYDAYFISHES)
//                                .document(document1.id)
//                                .collection(PATH_FISHESCOLLECTION)
//                                .get()
//                                .addOnCompleteListener { fishes ->
//                                    // count: 4
//                                    if (fishes.isSuccessful) {
//                                        for (document2 in fishes.result!!) {
//                                            Logger.d("document2 count => $count")
//                                            Logger.d("document2.data => ${document2.data}")
//                                            fishRecord.fishCategory =
//                                                document2.toObject((FishCategory::class.java))
//                                            list1.add(fishRecord)
//                                            Logger.d("fishRecord) $fishRecord")
//                                            Logger.d("list1) $list1}")
//                                            Logger.d("count => $count")
//
//                                            }
//                                        count -= 1
//                                        if(count==0){
//                                            Logger.d("continuation.resume list1) $list1}")
//                                            continuation.resume(Result1.Success(list1))
//                                        }
//
//                                    }
//
//                                }
//                            }
//                    }
//                }
//        }

    private const val PATH_EVERYDAYFISHES = "EverdayFishes"
    private const val PATH_FISHESCOLLECTION = "fishes"
    private const val KEY_CREATED_TIME = "time"

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFishRecord(): Result1<List<FishRecord>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_EVERYDAYFISHES)
                .get()
                .addOnCompleteListener { everydayFishes ->
                    if (everydayFishes.isSuccessful) {
                        val list1 = mutableListOf<FishRecord>()
                        var count = everydayFishes.result.size()
                        for (document1 in everydayFishes.result!!) {
//                            Logger.d("document1 count => $count")
                            Logger.d("document1.data => ${document1.data}")
                            val fishRecord = document1.toObject(FishRecord::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(PATH_EVERYDAYFISHES)
                                .document(document1.id)
                                .collection(PATH_FISHESCOLLECTION)
                                .get()
                                .addOnCompleteListener { fishes ->
                                    // count: 4
                                    if (fishes.isSuccessful) {
                                        val category = mutableListOf<FishCategory>()
                                        for (document2 in fishes.result!!) {

                                            Logger.d("document2 count => $count")
                                            Logger.d("document2.data => ${document2.data}")
                                            category.add(document2.toObject((FishCategory::class.java)))
                                        }
                                        fishRecord.fishCategory = category
                                        list1.add(fishRecord)
                                        Logger.d("fishRecord.fishCategory => ${fishRecord.fishCategory}")
                                        count -= 1
                                        if (count == 0) {
                                            Logger.d("continuation.resume list1) $list1}")
                                            continuation.resume(Result1.Success(list1))
                                        }

                                    }

                                }
                        }
                    }
                }
        }

    private const val PATH_FISHESCATEGORIES = "Categories"

    override suspend fun getFishAll(): Result1<List<Category>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_FISHESCATEGORIES)
                .get()
                .addOnCompleteListener { todayFishes ->
                    if (todayFishes.isSuccessful) {

                        var list = mutableListOf<Category>()

                        for (document1 in todayFishes.result!!) {

                            Logger.d("document1 $document1 ")
                            val category = document1.toObject(Category::class.java)
                            list.add(category)
                        }

                        continuation.resume(Result1.Success(list))
                    }
                }

        }

    override suspend fun getChatRecord(): Result1<ChatRecord> {
        TODO("Not yet implemented")
    }


}