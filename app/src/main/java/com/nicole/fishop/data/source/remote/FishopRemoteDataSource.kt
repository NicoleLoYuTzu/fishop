package com.nicole.fishop.data.source.remote

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.R
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
    override suspend fun getFishRecord(User: Users): Result1<List<FishRecord>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_EVERYDAYFISHES)
                .whereEqualTo("ownerId", User.id)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener { everydayFishes ->
                    val emptyList = listOf<FishRecord>()
                    if (everydayFishes.result.isEmpty){
                        continuation.resume(Result1.Success(emptyList))
                    }


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
                    } else {
                        todayFishes.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result1.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result1.Fail(FishopApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }

        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getFishTodayAll(): Result1<List<FishToday>> =
        suspendCoroutine { continuation ->
            val todayDate = getNowDate(System.currentTimeMillis())
            FirebaseFirestore.getInstance()
                .collection(PATH_EVERYDAYFISHES)
//                .whereEqualTo("date", "2022/07/06")
                .whereEqualTo("date", getNowDate(System.currentTimeMillis()))
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
                                    } else {
                                        fishes.exception?.let {

                                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                            continuation.resume(Result1.Error(it))
                                            return@addOnCompleteListener
                                        }
                                        continuation.resume(
                                            Result1.Fail(
                                                FishopApplication.instance.getString(
                                                    R.string.you_know_nothing
                                                )
                                            )
                                        )
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
                .whereEqualTo("date", "2022/07/06")
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
                                if (todaySeller.isSuccessful) {
                                    var fishTodaySeller = FishToday()
                                    for (document2 in todaySeller.result!!) {
                                        Logger.d("getFishTodayFilterAll document1.data => ${document1.data}")
                                        fishTodaySeller = document2.toObject(FishToday::class.java)
                                        fishTodaySeller.category = listOf(fishTodayCategory)
                                    }
                                    list.add(fishTodaySeller)
                                    continuation.resume(Result1.Success(list))
                                    Logger.d("getFishTodayFilterAll continuation.resume list) $list}")
                                } else {
                                    todaySeller.exception?.let {

                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        continuation.resume(Result1.Error(it))
                                        return@addOnCompleteListener
                                    }
                                    continuation.resume(
                                        Result1.Fail(
                                            FishopApplication.instance.getString(
                                                R.string.you_know_nothing
                                            )
                                        )
                                    )
                                }
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
                    if (SellerInfo.isSuccessful) {
                        Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
                        var sellerLocation = SellerLocation()
                        for (document2 in SellerInfo.result!!) {
                            sellerLocation = document2.toObject(SellerLocation::class.java)
                        }
                        continuation.resume(Result1.Success(sellerLocation))
                    } else {
                        SellerInfo.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result1.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result1.Fail(FishopApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }

        }

    override suspend fun getAllSellerAddressResult(ownerId: List<String>): Result1<List<SellerLocation>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_USERS)
                .whereIn("id", ownerId)
                .get()
                .addOnCompleteListener { SellerInfo ->
                    Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
                    Logger.d("sellerId => $ownerId")
                    if (SellerInfo.isSuccessful) {
                        var sellersLocation = mutableListOf<SellerLocation>()
                        var sellerLocation = SellerLocation()
                        for (document2 in SellerInfo.result!!) {
                            sellerLocation = document2.toObject(SellerLocation::class.java)
                            sellersLocation.add(sellerLocation)
                        }
                        continuation.resume(Result1.Success(sellersLocation))
                    } else {
                        SellerInfo.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result1.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result1.Fail(FishopApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }

        }

    override suspend fun setTodayFishRecord(
        fishToday: FishToday,
        Categories: List<FishTodayCategory>,
        User: Users
    ): Result1<Boolean> = suspendCoroutine { continuation ->
        val fishTodays = FirebaseFirestore.getInstance().collection(PATH_EVERYDAYFISHES)
        val document = fishTodays.document()

        fishToday.id = document.id
        fishToday.date = getNow()
        fishToday.time = System.currentTimeMillis().toString()
        fishToday.ownerId = User.id.toString()
        Logger.i("fishToday.id ${fishToday.id}")
        document
            .set(fishToday)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("setTodayFishRecord: $fishTodays")

                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        return@addOnCompleteListener
                    }
                }
            }

        val fishTodayCategories = FirebaseFirestore.getInstance().collection(PATH_FISHESCOLLECTION)
        for (i in Categories) {
            var count = Categories.size
            val fishTodayCategoriesDocument = fishTodayCategories.document()
            i.id = fishTodayCategoriesDocument.id
            i.date = getNowDate(System.currentTimeMillis())
            i.tfId = User.id.toString()
            document.collection(PATH_FISHESCOLLECTION).document(fishTodayCategoriesDocument.id)
                .set(i).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("fishTodayCategoriesDocument.id ${fishTodayCategoriesDocument.id}")
                        Logger.i("setTodayFishRecord: $fishTodays")
                        Logger.i("i $i")
                        count -= 1
                        if (count == 0) {
                            continuation.resume(Result1.Success(true))
                        }
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
    }


    override suspend fun userSignIn(users: Users): Result1<Users> =
        suspendCoroutine { continuation ->
            val userStart = FirebaseFirestore.getInstance().collection(PATH_USERS)
            val document = userStart.document()
            users.id = document.id
            document
                .set(users)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Logger.i("users.id: ${users.id}")
                        Logger.i("userStart: $userStart")
//                    var salerInfo = Users()
//                        salerInfo = task.toObject(Users::class.java)
                        FirebaseFirestore.getInstance()
                            .collectionGroup(PATH_USERS)
                            .whereEqualTo("id", users.id)
                            .get()
                            .addOnCompleteListener { SellerInfo ->
                                if (SellerInfo.isSuccessful) {
                                    for (document2 in SellerInfo.result!!) {
                                        val salerInfo = document2.toObject(Users::class.java)
                                        Logger.i("salerInfo $salerInfo")
                                        continuation.resume(Result1.Success(salerInfo))
                                    }

                                } else {
                                    SellerInfo.exception?.let {
                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        return@addOnCompleteListener
                                    }
                                }
                            }
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun getSalerInfo(users: Users): Result1<Users> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_USERS)
                .whereEqualTo("id", users.id)
//               懶得一直刪, 先全部都找同一筆資料
//                .whereEqualTo("id", users.id)
                .get()
                .addOnCompleteListener { SellerInfo ->
                    Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
                    var salerInfo = Users()
                    for (document2 in SellerInfo.result!!) {
                        salerInfo = document2.toObject(Users::class.java)
                        continuation.resume(Result1.Success(salerInfo))
                        Logger.i("getSalerInfo salerInfo => ${salerInfo}")
                    }

                }

        }

    override suspend fun setSalerInfo(users: Users): Result1<Boolean> = suspendCoroutine {
//        36rzFkt6jRyei8GYjz6X
            continuation ->
        FirebaseFirestore.getInstance()
            .collectionGroup(PATH_USERS)
            .whereEqualTo("id", users.id)
//            .whereEqualTo("email", "a4207486@gmail.com")
            .get()
            .addOnCompleteListener { SellerInfo ->
                Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
                for (oldDocument in SellerInfo.result) {
                    Logger.i("oldDocument $oldDocument")
                    Logger.i("SellerInfo.result ${SellerInfo.result}")
                    val oldUsers = oldDocument.toObject(Users::class.java)
                    Logger.i("oldUsers => $oldUsers")

                    oldUsers.id?.let {
                        FirebaseFirestore.getInstance()
                            .collection(PATH_USERS)
                            .document(it)
                            .update(
                                mapOf(
                                    "address" to users.address,
                                    "businessTime" to users.businessTime,
                                    "businessEndTime" to users.businessEndTime,
                                    "name" to users.name,
                                    "phone" to users.phone,
                                    "businessDay" to users.businessDay,
                                )
                            )
                            .addOnCompleteListener { result ->
                                if (result.isSuccessful) {
                                    Logger.i(" oldUsers.email => ${oldUsers.email}")
                                    Logger.i("setSalerInfo result.result => ${result.result}")

                                } else {
                                    result.exception?.let {

                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        return@addOnCompleteListener
                                    }
                                }
                                continuation.resume(Result1.Success(true))
                            }

                    }
                }
            }
//          刪除無效資料
//        FirebaseFirestore.getInstance()
//            .collectionGroup(PATH_USERS)
//            .whereEqualTo("address", "")
//            .get()
//            .addOnCompleteListener { SellerInfo ->
//                Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
//                for (oldDocument in SellerInfo.result) {
//                    Logger.i("oldDocument $oldDocument")
//                    Logger.i("SellerInfo.result ${SellerInfo.result}")
//                    val oldUsers = oldDocument.toObject(Users::class.java)
//                    oldUsers.id?.let {
//                        FirebaseFirestore.getInstance()
//                            .collection(PATH_USERS)
//                            .document(it)
//                            .delete()
//                            .addOnCompleteListener { result ->
//                                Logger.i(" oldUsers.email => ${oldUsers.email}")
//                                if (result.isSuccessful) {
//                                    Logger.i("result.result => ${result.result}")
//                                }
//                            }
//
//                    }
//
//                }
//                continuation.resume(Result1.Success(true))
//            }
    }

    private const val PATH_CHATS = "chats"
    private const val PATH_CHATROOMS = "ChatRooms"
    private const val PATH_MEMBERSID = "membersId"
    private const val PATH_SALERID = "saler"
    private const val PATH_BUYERID = "buyer"

    override suspend fun getChatBoxRecord(
        salerFishToday: FishToday,
        user: Users
    ): Result1<List<ChatBoxRecord>> =
        suspendCoroutine { continuation ->
            Logger.d("user.id ${user.id} ")
            Logger.d("salerFishToday.id ${salerFishToday.id} ")

            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_CHATROOMS)
//                .whereArrayContainsAny(PATH_MEMBERSID,mutableArray )
                .whereEqualTo(PATH_SALERID, salerFishToday.ownerId)
                .whereEqualTo(PATH_BUYERID, user.id)
                .get()
                .addOnCompleteListener { Result ->
                    Logger.d("getChatBoxRecord.Result ${Result.result.documents} ")

                    for (chatRecord in Result.result) {
                        Logger.i("chatRecord $chatRecord")
                        Logger.i("Result.result ${Result.result}")

//                            if (chatRecord.data.filterNot)

                        val chatRecordResult = chatRecord.toObject(ChatRecord::class.java)

                        Logger.i("chatRecordResult => $chatRecordResult")
                        Logger.i("chatRecordResultResult.result => ${Result.result.documents}")


                        chatRecordResult.id.let {
                            FirebaseFirestore.getInstance()
                                .collection(PATH_CHATROOMS)
                                .document(it)
                                .collection(PATH_CHATS)
                                .get()
                                .addOnCompleteListener { Result ->
                                    var chatBoxRecord = ChatBoxRecord()
//                                    var count = Result.result.size()
//                                    Logger.i("count chatRecordResult => $count")
                                    val chatBoxList = mutableListOf<ChatBoxRecord>()
                                    for (i in Result.result!!) {
                                        Logger.i("chatBoxRecord Result.result ${Result.result}")
                                        Logger.i("chatBoxRecord i ${i}")
                                        chatBoxRecord = i.toObject(ChatBoxRecord::class.java)
                                        chatBoxList.add(chatBoxRecord)
//                                        chatRecordResult.chats = chatBoxList
                                    }
                                    continuation.resume(Result1.Success(chatBoxList))
                                    Logger.i("count == 0 chatRecordResult => $chatRecordResult")

                                    Logger.i("getChatBoxRecord => $chatRecordResult")
                                    Logger.i("chatRecordResult.chats => ${chatRecordResult.chats}")

                                }
                        }
                    }
                }
        }

    override suspend fun getSalerChatRecordResult(user: Users): Result1<List<ChatRecord>> =
        suspendCoroutine { continuation ->
            Logger.d("user.id ${user.id} ")
//
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOMS)
                .whereEqualTo(PATH_SALERID, user.id)
                .get()
                .addOnCompleteListener { Result ->
                    Logger.d("getSalerChatRecordResult.Result ${Result.result.documents} ")
                    var chatRecordResult = ChatRecord()
                    val chatsRecord = mutableListOf<ChatRecord>()
                    for (chatRecord in Result.result) {
                        Logger.i("chatRecord $chatRecord")
                        Logger.i("Result.result ${Result.result}")
                        chatRecordResult = chatRecord.toObject(ChatRecord::class.java)
                        chatsRecord.add(chatRecordResult)
                    }
                    continuation.resume(Result1.Success(chatsRecord))
                }
        }

    override suspend fun getBuyerChatRecordResult(user: Users): Result1<List<ChatRecord>> =
        suspendCoroutine { continuation ->
            Logger.d("user.id ${user.id} ")
//
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOMS)
                .whereEqualTo(PATH_BUYERID, user.id)
                .get()
                .addOnCompleteListener { Result ->
                    Logger.d("getSalerChatRecordResult.Result ${Result.result.documents} ")
                    var chatRecordResult = ChatRecord()
                    val chatsRecord = mutableListOf<ChatRecord>()
                    for (chatRecord in Result.result) {
                        Logger.i("chatRecord $chatRecord")
                        Logger.i("Result.result ${Result.result}")
                        chatRecordResult = chatRecord.toObject(ChatRecord::class.java)
                        chatsRecord.add(chatRecordResult)
                    }
                    continuation.resume(Result1.Success(chatsRecord))
                }
        }


    override suspend fun addChatroom(
        chatRecord: ChatRecord
    ): Result1<ChatRecord> =
        suspendCoroutine { continuation ->
            val openNewChatRoom = FirebaseFirestore.getInstance().collection(PATH_CHATROOMS)
            val document = openNewChatRoom.document()
            chatRecord.id = document.id
            document
                .set(chatRecord)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("chatRecord.id: ${chatRecord.id}")
                        Logger.i("openNewChatRoom: $openNewChatRoom")
                        FirebaseFirestore.getInstance()
                            .collectionGroup(PATH_CHATROOMS)
                            .whereEqualTo("id", chatRecord.id)
                            .get()
                            .addOnCompleteListener { getNewRoomReady ->
                                if (getNewRoomReady.isSuccessful) {
                                    for (document2 in getNewRoomReady.result!!) {
                                        val openNewChatRecord =
                                            document2.toObject(ChatRecord::class.java)
                                        Logger.i("openNewChatRecord $openNewChatRecord")
                                        continuation.resume(Result1.Success(openNewChatRecord))
                                    }

                                } else {
                                    getNewRoomReady.exception?.let {
                                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        return@addOnCompleteListener
                                    }
                                }
                            }
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            return@addOnCompleteListener
                        }
                    }
                }
        }

    override suspend fun sendChat(chatRoomId: String, chat: ChatBoxRecord): Result1<Boolean> =
        suspendCoroutine { continuation ->
            val openNewChatRoom = FirebaseFirestore.getInstance().collection(PATH_CHATROOMS)
            val document = openNewChatRoom.document()
            chat.id = document.id

            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOMS)
//                .whereArrayContainsAny(PATH_MEMBERSID,mutableArray )
                .document(chatRoomId)
                .collection(PATH_CHATS)
                .document()
                .set(chat)
                .addOnCompleteListener { Result ->
                    Logger.d("sendChat ${Result.result} ")
                    if (Result.isSuccessful) {
                        continuation.resume(Result1.Success(true))
                    }
                }
        }

    override suspend fun sendLastChat(chatRoomId: String, chat: ChatRecord): Result1<ChatRecord> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOMS)
//                .whereArrayContainsAny(PATH_MEMBERSID,mutableArray )
                .document(chatRoomId)
                .update(
                    mapOf(
                        "lastchat" to chat.lastchat,
                        "lastchatTime" to chat.lastchatTime,
                        "lastsender" to chat.lastsender,
                        "lastsenderName" to chat.lastsenderName,
                        "salerPhoto" to chat.salerPhoto
                    )
                )
                .addOnCompleteListener { Result ->
                    Logger.d("sendLastChat ${Result.result} ")
                    if (Result.isSuccessful) {
                        FirebaseFirestore.getInstance()
                            .collection(PATH_CHATROOMS)
                            .document(chatRoomId)
                            .get()
                            .addOnCompleteListener { getLastTimeRecord ->
                                var ChatLastTimeRecord = ChatRecord()
                                if (getLastTimeRecord.isSuccessful) {
                                    ChatLastTimeRecord =
                                        getLastTimeRecord.result.toObject(ChatRecord::class.java)!!
                                }
                                Logger.d("getLastTimeRecord.result ${getLastTimeRecord.result} ")
                                Logger.d("ChatLastTimeRecord.result ${ChatLastTimeRecord} ")
                                continuation.resume(Result1.Success(ChatLastTimeRecord))
                            }

                    }
                }
        }

    override suspend fun checkHasRoom(salerId: String, userId: String): Result1<ChatRecord> =
        suspendCoroutine { continuation ->
            Logger.d("salerId.id ${salerId} ")
            Logger.d("userId.id ${userId} ")
            FirebaseFirestore.getInstance()
                .collectionGroup(PATH_CHATROOMS)
                .whereEqualTo(PATH_SALERID, salerId)
                .whereEqualTo(PATH_BUYERID, userId)
                .get()
                .addOnCompleteListener { checkHasRoom ->
                    var ChatLastTimeRecord = ChatRecord()
                    if (checkHasRoom.isSuccessful) {
                        if (checkHasRoom.result.isEmpty) {
                            continuation.resume(Result1.Fail("..."))
                        } else {
                            for (document in checkHasRoom.result) {
                                ChatLastTimeRecord =
                                    document.toObject(ChatRecord::class.java)!!
                                continuation.resume(Result1.Success(ChatLastTimeRecord))

                            }
                        }
                    }
                }
        }

    override fun getLiveChat(chatRoomId: String): MutableLiveData<List<ChatBoxRecord>> {
        val liveData = MutableLiveData<List<ChatBoxRecord>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOMS)
            .document(chatRoomId)
            .collection(PATH_CHATS)
            .orderBy(KEY_CREATED_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapsot, exception ->
                val list = mutableListOf<ChatBoxRecord>()
                for (document in snapsot!!) {
                    val chat = document.toObject(ChatBoxRecord::class.java)
                    list.add(chat)
                }

                liveData.value = list

            }
        return liveData
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

    @SuppressLint("SimpleDateFormat")
    fun getNow(): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("yyyy/MM/dd").format(Date())
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


