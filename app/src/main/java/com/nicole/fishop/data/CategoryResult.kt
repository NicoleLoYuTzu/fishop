package com.nicole.fishop.data

import android.os.Parcelable
import com.nicole.fishop.util.Logger
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryResult(
    val error: String? = null,
    var CategoryList: List<Category>? = null
) : Parcelable {

//    fun toCategoryItems(): List<AddTodayItem> {
//        val items1 = mutableListOf<AddTodayItem>()
//
//        CategoryList.let {
////            if (it != null) {
////                for(i in it){
////                    items.add(AddTodayItem.CategoryName(i))
////                    items.add(AddTodayItem.CategoryItems(i))
////                }
////            }
//            if (it != null) {
//                for ((id,categoryName, items) in it) {
//                    items1.add(AddTodayItem.CategoryName(it))
//                    for (categoryItem in items) {
////                        when (index % 2) {
////                            0 -> items.add(HomeItem.FullProduct(product))
////                            1 -> items.add(HomeItem.CollageProduct(product))
////                        }
//                        items1.add(AddTodayItem.CategoryItems(categoryItem))
//                    }
//                }
//            }
//
//
//
//
//        }
//        return items1
//    }
}