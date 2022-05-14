package com.easycash.footballleague.ui.competitionsInfo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Winner(
    val crestUrl: String,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String
): Parcelable