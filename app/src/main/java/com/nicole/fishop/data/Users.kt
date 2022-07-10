package com.nicole.fishop.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp



@Parcelize
data class Users(
    var id: String?=null,
    var accountType: String?=null,
    var address: String?=null,
    var name: String?=null,
    var phone: String?=null,
    var businessTime: String?=null,
    var email: String?= null,
    var businessEndTime: String? = null,
    var businessDay:List<String>? = emptyList(),
    var picture: String? = null
) : Parcelable