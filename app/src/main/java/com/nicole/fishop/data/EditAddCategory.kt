package com.nicole.fishop.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class EditAddCategory(
    val price: String,
    val amount: String,
    val unit: String,
    val category: String
) : Parcelable
