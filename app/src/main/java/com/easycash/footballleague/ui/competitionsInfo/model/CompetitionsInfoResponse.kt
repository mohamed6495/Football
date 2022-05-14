package com.easycash.footballleague.ui.competitionsInfo.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.easycash.footballleague.ui.competitions.model.Area
import com.easycash.footballleague.ui.competitions.model.CurrentSeason
import kotlinx.android.parcel.Parcelize

@Entity
@Keep
@Parcelize
data class CompetitionsInfoResponse(
    @PrimaryKey
    var id: Int=0,
    val area: Area?,
    val code: String?,
    val currentSeason: CurrentSeason?,
    val emblemUrl: String?,
    val lastUpdated: String?,
    val name: String?,
    val plan: String?,
    val message: String?,
    val seasons: List<Season>?
): Parcelable