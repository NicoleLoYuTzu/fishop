package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ChatRecord(
    var id: String = "",
) : Parcelable