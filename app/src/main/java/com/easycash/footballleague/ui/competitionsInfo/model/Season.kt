package com.easycash.footballleague.ui.competitionsInfo.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class Season(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Winner
): Parcelable