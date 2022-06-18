package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FishCategory(
    var id: String = "",
    var amount: String = "",
    var category: String = "",
    var saleprice: String = "",
    var unit: String = "",
    var yuan: String = ""
) : Parcelable