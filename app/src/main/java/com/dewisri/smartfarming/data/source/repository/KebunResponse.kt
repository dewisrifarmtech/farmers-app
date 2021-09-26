package com.dewisri.smartfarming.data.source.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KebunRequest(
    var name: String = "",
    var location :String = ""
   
): Parcelable
