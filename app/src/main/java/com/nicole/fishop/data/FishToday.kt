package com.nicole.fishop.data

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

//for buyer
@Parcelize
data class FishToday(
    var id: String = "",
    var ownerId: String = "",
    var ownPhoto: String = "",
    var time: String = "",
    var name: String = "",
    var category: List<FishTodayCategory> = emptyList(),
    var distance: Long = 0,
    var date: String =""
) : Parcelable{

}