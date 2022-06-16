package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FishCategory(
    var id: String = "",
) : Parcelable