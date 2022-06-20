package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Time
import java.sql.Timestamp
import java.util.*

//for seller
@Parcelize
data class FishRecord(
    var id: String = "",
    var ownerId: String = "",
    var time: Date = Timestamp(0),
    var fishCategory:List<FishCategory> = listOf(FishCategory("","","","","","")) ,
) : Parcelable{

}