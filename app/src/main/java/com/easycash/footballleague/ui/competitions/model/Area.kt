package com.easycash.footballleague.ui.competitions.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Area(
    val countryCode: String,
    val ensignUrl: String,
    val id: Int,
    val name: String
): Parcelable