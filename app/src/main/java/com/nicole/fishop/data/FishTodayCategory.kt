package com.nicole.fishop.data

import android.os.Parcelable
import java.sql.Timestamp
import java.util.*
import kotlinx.parcelize.Parcelize

// for buyer
@Parcelize
data class FishTodayCategory(
    var id: String = "",
    var amount: String = "",
    var category: String = "",
    var saleprice: String = "",
    var unit: String = "",
    var yuan: String = "",
    var date: Date = Timestamp(0),
    var tfId: String = "",
) : Parcelable
