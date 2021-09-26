package com.dewisri.smartfarming.data.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenItem(
    val title: String,
    val description: String,
    val screenImg: Int
    ): Parcelable