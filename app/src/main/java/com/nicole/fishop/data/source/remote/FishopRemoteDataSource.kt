package com.nicole.fishop.data.source.remote

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.nicole.fishop.data.*
import com.nicole.fishop.data.source.FishopDataSource
import com.nicole.fishop.util.Logger
import java.text.SimpleDateFormat
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

//                        var todayItem = CategoryResult()
                        var list = mutableListOf<Category>()

                        for (document1 in todayFishes.result!!) {

                            Logger.d("document1 $document1 ")
                            val category = document1.toObject(Category::class.java)
                            list.add(category)
//                            todayItem.CategoryList = listOf(category)
                        }

                        continuation.resume(Result1.Success(list))
                    }
                }

        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFishTodayAll(): Result1<List<FishToday>> =
        suspendCoroutine { continuation ->
            val todayDate = getNowDate(System.currentTimeMillis())
            FirebaseFirestore.getInstance()
                .collection(PATH_EVERYDAYFISHES)
//                .whereLessThan("time", System.currentTimeMillis())
//                .whereGreaterThan("time",System.currentTimeMillis()-86400000)
                .whereEqualTo("date", "2022/06/22")
                .get()
                .addOnCompleteListener { todaySeller ->
                    Logger.d("${System.currentTimeMillis()}")
                    if (todaySeller.isSuccessful) {
                        var list = mutableListOf<FishToday>()
                        var count = todaySeller.result.size()
                        for (document1 in todaySeller.result!!) {
                            Logger.d("getFishTodayAll document1 $document1 ")
                            val fishToday = document1.toObject(FishToday::class.java)
                            FirebaseFirestore.getInstance()
                                .collection(PATH_EVERYDAYFISHES)
                                .document(document1.id)
                                .collection(PATH_FISHESCOLLECTION)
                                .get()
                                .addOnCompleteListener { fishes ->
                                    // count: 4
                                    if (fishes.isSuccessful) {
                                        val categories = mutableListOf<FishTodayCategory>()
                                        for (document2 in fishes.result!!) {
                                            Logger.d("document2.data => ${document2.data}")
                                            categories.add(document2.toObject((FishTodayCategory::class.java)))
                                        }
                                        fishToday.category = categories

                                        list.add(fishToday)
                                        Logger.d("fishToday.category => ${fishToday.category}")
                                        count -= 1
                                        if (count == 0) {
                                            Logger.d("continuation.resume list) $list}")
                                            continuation.resume(Result1.Success(list))
                                        }
                                    }
                                }
                        }
                    }
                }

        }

    //上架前改成當天日期, 下面第二行註解取消
    override suspend fun getFishTodayFilterAll(fish: String): Result1<List<FishToday>> =
        suspendCoroutine { continuation ->
//            val todayDate = getNowDate(System.currentTimeMillis())
            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_FISHESCOLLECTION)
                .whereEqualTo("category", fish)
                .whereEqualTo("date", "2022/06/22")
                .get()
                .addOnCompleteListener { todayCategory ->
                    var list = mutableListOf<FishToday>()
                    Logger.d("category ${fish} ")
                    Logger.d("todayCategory.documents ${todayCategory.result.documents} ")
                    Logger.d("todayCategory.documents ${todayCategory.result} ")
                    for (document1 in todayCategory.result!!) {
                        Logger.d("document1.data => ${document1.data}")
                        val fishTodayCategory = document1.toObject(FishTodayCategory::class.java)
                        FirebaseFirestore.getInstance()
                            .collectionGroup(PATH_EVERYDAYFISHES)
                            .whereIn("id", listOf(fishTodayCategory.tfId))
                            .get()
                            .addOnCompleteListener { todaySeller ->
                                var fishTodaySeller = FishToday()
                                for (document2 in todaySeller.result!!) {
                                    Logger.d("getFishTodayFilterAll document1.data => ${document1.data}")
                                    fishTodaySeller = document2.toObject(FishToday::class.java)
                                    fishTodaySeller.category = listOf(fishTodayCategory)
                                }
                                list.add(fishTodaySeller)
                                continuation.resume(Result1.Success(list))
                                Logger.d("getFishTodayFilterAll continuation.resume list) $list}")
                            }
                    }
                }
                .addOnFailureListener {
                    Logger.d(".addOnFailureListener) ${it.message}}")
                }
        }

    override suspend fun getChatRecord(): Result1<ChatRecord> =
        suspendCoroutine { continuation ->

        }

    private const val PATH_USERS = "Users"
    override suspend fun getGoogleMap(sellerId: String): Result1<SellerLocation> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_USERS)
                .whereEqualTo("id", sellerId)
                .get()
                .addOnCompleteListener { SellerInfo ->
                    Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
                    var sellerLocation = SellerLocation()
                    for (document2 in SellerInfo.result!!) {
                        sellerLocation = document2.toObject(SellerLocation::class.java)
                    }
                    continuation.resume(Result1.Success(sellerLocation))
                }

        }


    @SuppressLint("SimpleDateFormat")
    private fun getNowDate(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("yyyy/MM/dd").format(time)
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
        }
    }
}

