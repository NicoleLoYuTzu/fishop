package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp


@Parcelize
data class FishRecord(
    var id: String,
    var createdTime: Timestamp,
    var category: String,
) : Parcelable