package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//for seller
@Parcelize
data class FishCategory(
    var id: String = "",
    var date:String = "",
    var tfId:String = "",
    var amount: String = "",
    var category: String = "",
    var saleprice: String = "",
    var unit: String = "",
    var yuan: String = "",
    var name: String = ""
) : Parcelable