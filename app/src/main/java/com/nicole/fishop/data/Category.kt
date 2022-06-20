package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//for seller

data class Category(
    var id: String = "",
    var categoryName: String = "",
    var items :List<CategoryItem> = emptyList()
//    var items: ArrayList<FishAllItems> = arrayListOf(FishAllItems(arrayOf(""),"")),
//    var items: HashMap<String,FishAllItems> = mapOf("childItems" to FishAllItems()) as HashMap<String, FishAllItems>,
//    var items: List<String> =
//    val childItems: List<Int> = listOf(1,2,3) ,
//    val title: String = ""
)
