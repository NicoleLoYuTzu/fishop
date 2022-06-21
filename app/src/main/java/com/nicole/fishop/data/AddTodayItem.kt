package com.nicole.fishop.data

/**
 * Created by Wayne Chen in Jul. 2019.
 */
sealed class AddTodayItem {

    abstract val id: Long

    data class CategoryName(val category: String) : AddTodayItem() {
        override val id: Long = -1
    }
    data class CategoryTitle(val title: String) : AddTodayItem() {
        override val id: Long = -1
//            get() = categoryItem.id.toLong()
    }
}
