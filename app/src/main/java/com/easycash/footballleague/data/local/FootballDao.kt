package com.easycash.footballleague.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.easycash.footballleague.ui.competitions.model.CompetitionsResponse
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import com.easycash.footballleague.utils.room.BaseDao

@Dao
abstract class CompetitionsDao : BaseDao<CompetitionsResponse> {
    @Query("SELECT * FROM CompetitionsResponse")
    abstract fun getCompetitionsCash(): LiveData<CompetitionsResponse>
}

@Dao
abstract class CompetitionsInfoDao : BaseDao<CompetitionsInfoResponse> {

    @Query("SELECT * FROM CompetitionsInfoResponse  WHERE id IN(:id)")
    abstract fun getCompetitionsInfoCash(id:Int): LiveData<CompetitionsInfoResponse>
}

@Dao
abstract class TeamDao : BaseDao<TeamsResponse> {
    @Query("SELECT * FROM TeamsResponse  WHERE id IN(:id)")
    abstract fun getTeamCash(id:Int): LiveData<TeamsResponse>
}