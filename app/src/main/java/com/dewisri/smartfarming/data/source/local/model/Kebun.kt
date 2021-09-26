package com.dewisri.smartfarming.data.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kebun(
    var name: String = "",
    var location :String = ""
):Parcelable
