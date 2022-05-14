package com.easycash.footballleague.ui.competitions.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Keep
@Parcelize
data class CompetitionsResponse(
    @PrimaryKey
    var id: Int = 0,
    val competitions: List<Competition>,
    val count: Int,
): Parcelable