package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*


@Parcelize
data class FishToday(
    var id: String = "",
    var ownerId: String = "",
    var time: Date = Timestamp(0),
    var category: FishCategory= FishCategory("","","","","",""),
) : Parcelable {

}