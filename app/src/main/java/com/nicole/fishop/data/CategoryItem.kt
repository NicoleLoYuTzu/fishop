package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(
    val childItems: List<String> = emptyList(),
    val title: String = "",
) : Parcelable {

}
