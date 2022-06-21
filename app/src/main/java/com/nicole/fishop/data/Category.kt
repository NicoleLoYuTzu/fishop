package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//for seller
@Parcelize
data class Category(
    var id: String = "",
    var categoryName: String = "",
    var items: List<CategoryItem> = emptyList()
) : Parcelable {


//    fun toCategoryName(): List<AddTodayItem> {
//        val items1 = mutableListOf<AddTodayItem>()
//
//        categoryName.let {
//            for (items in it) {
//                items1.add(AddTodayItem.CategoryName(it))
//            }
//        }
//        return items1
//    }
}

