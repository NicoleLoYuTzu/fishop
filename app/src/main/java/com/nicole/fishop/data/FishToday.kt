package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

//for buyer
@Parcelize
data class FishToday(
    var id: String = "",
    var ownerId: String = "",
    var time: String = "",
    var name: String = "",
    var category: List<FishTodayCategory> = listOf(FishTodayCategory("","","","","","")) ,
) : Parcelable{

}