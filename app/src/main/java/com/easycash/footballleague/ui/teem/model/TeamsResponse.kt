package com.easycash.footballleague.ui.teem.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.android.parcel.Parcelize

@Entity
@Keep
@Parcelize
data class TeamsResponse(
    @PrimaryKey
    var id: Int=0,
    val count: Int,
    val teams: List<Team>
): Parcelable
