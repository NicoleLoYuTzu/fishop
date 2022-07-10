package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatBoxRecord(
    var content: String = "",
    var id: String = "",
    var sender: String = "",
    var time: Long = 0,
    var senderphoto: String = ""
) : Parcelable