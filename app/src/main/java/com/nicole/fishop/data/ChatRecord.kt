package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ChatRecord(
    var id: String = "",
    var chats: List<ChatBoxRecord> = emptyList(),
    var saler: String ="",
    var buyer: String ="",
    var lastchat: String ="",
    var lastchatTime: String = "",
    var lastsender: String = "",
    var lastsenderName: String = "",
    var salerName: String ="",
    var buyerName: String = "",
    var salerPhoto: String = "",
    var buyerPhoto:String =""
) : Parcelable