package com.easycash.footballleague.ui.competitions.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize


@Parcelize
data class CurrentSeason(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,

): Parcelable