package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(
    val childItems: List<String> = emptyList(),
    val title: String = "",
) : Parcelable {


//    fun toCategoryItems(): List<AddTodayItem> {
//        val items1 = mutableListOf<AddTodayItem>()
//
//        title.let {
//            for (items in it) {
//                items1.add(AddTodayItem.CategoryName(it))
//            }
//        }
//        return items1
//    }
}
