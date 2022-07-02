package com.nicole.fishop.data

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
    var businessTime: String?=null
) : Parcelable