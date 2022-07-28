package com.nicole.fishop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// for buyer
@Parcelize
data class SellerLocation(
    var accountType: String = "",
    var address: String? = "",
    var id: String? = "",
    var name: String? = "",
    var phone: String? = "",
) : Parcelable
