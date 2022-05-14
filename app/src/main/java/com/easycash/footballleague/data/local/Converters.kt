package com.easycash.footballleague.data.local

import androidx.room.TypeConverter
import com.easycash.footballleague.ui.competitions.model.Area
import com.easycash.footballleague.ui.competitions.model.Competition
import com.easycash.footballleague.ui.competitions.model.CurrentSeason
import com.easycash.footballleague.ui.competitionsInfo.model.Season
import com.easycash.footballleague.ui.competitionsInfo.model.Winner
import com.easycash.footballleague.ui.teem.model.Team
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CompetitionListConverter {
    private val type = object : TypeToken<List<Competition>>() {}.type

    @TypeConverter
    fun toJson(obj: List<Competition>): String? = Gson().toJson(obj, type)

    @TypeConverter
    fun toList(json: String?): List<Competition> = Gson().fromJson(json, type)
}

class CurrentSeasonConverter {
    private val type = object : TypeToken<CurrentSeason>() {}.type

    @TypeConverter
    fun toJson(data: CurrentSeason?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): CurrentSeason? {
        return Gson().fromJson(json, type)
    }
}

class SeasonListConverter {
    private val type = object : TypeToken<List<Season>>() {}.type

    @TypeConverter
    fun toJson(obj: List<Season>): String? = Gson().toJson(obj, type)

    @TypeConverter
    fun toList(json: String?): List<Season> = Gson().fromJson(json, type)
}

class WinnerConverter {
    private val type = object : TypeToken<Winner>() {}.type

    @TypeConverter
    fun toJson(data: Winner?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): Winner? {
        return Gson().fromJson(json, type)
    }
}

class AreaConverter {
    private val type = object : TypeToken<Area>() {}.type

    @TypeConverter
    fun toJson(data: Area?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): Area? {
        return Gson().fromJson(json, type)
    }
}

class TeamListConverter {
    private val type = object : TypeToken<List<Team>>() {}.type

    @TypeConverter
    fun toJson(obj: List<Team>): String? = Gson().toJson(obj, type)

    @TypeConverter
    fun toList(json: String?): List<Team> = Gson().fromJson(json, type)
}
