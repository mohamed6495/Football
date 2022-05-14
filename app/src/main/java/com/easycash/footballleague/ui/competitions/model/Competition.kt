package com.easycash.footballleague.ui.competitions.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize



@Parcelize
data class Competition(
    val area: Area,
    val code: String,
    val currentSeason: CurrentSeason,
    val emblemUrl: String,
    val lastUpdated: String,
    val name: String,
    val id: Int,
    val numberOfAvailableSeasons: Int,
    val plan: String
): Parcelable