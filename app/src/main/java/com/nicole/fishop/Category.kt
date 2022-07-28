package com.nicole.fishop

data class Category(
    val title: String,
    val childItems: ChildCategory
)

data class ChildCategory(
    val title: String
)
