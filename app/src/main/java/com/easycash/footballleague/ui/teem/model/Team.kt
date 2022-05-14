package com.easycash.footballleague.ui.teem.model

import android.os.Parcelable
import com.easycash.footballleague.ui.competitions.model.Area
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val address: String,
    val area: Area,
    val clubColors: String,
    val crestUrl: String,
    val email: String,
    val founded: Int,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val phone: String,
    val shortName: String,
    val tla: String,
    val venue: String,
    val website: String
): Parcelable