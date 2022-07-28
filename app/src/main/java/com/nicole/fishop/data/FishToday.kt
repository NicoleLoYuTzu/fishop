package com.nicole.fishop.data

import android.os.Parcelable
import java.sql.Timestamp
import java.util.*
import kotlinx.parcelize.Parcelize

// for buyer
@Parcelize
data class FishToday(
    var id: String = "",
    var ownerId: String = "",
    var ownPhoto: String = "",
    var time: Date = Timestamp(0),
    var name: String = "",
    var category: List<FishTodayCategory> = emptyList(),
    var distance: Long = 0,
    var date: String = "",
    var buyerId: String = ""
) : Parcelable
