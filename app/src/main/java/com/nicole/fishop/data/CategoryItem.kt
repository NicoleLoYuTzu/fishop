package com.nicole.fishop.data

import android.os.Parcelable
import com.nicole.fishop.fishSeller.FishSellerViewModelAddToday
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class CategoryItem(
    val childItems: List<String> = emptyList(),
    val title: String = "",
    val unit: String = ""
) : Parcelable {
}
