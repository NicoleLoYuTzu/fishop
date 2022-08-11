package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResult(
    val error: String? = null,
    var CategoryList: List<Category>? = null
) : Parcelable {

}
