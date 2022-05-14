package com.easycash.footballleague.data.repository

import com.easycash.footballleague.data.local.CompetitionsDao
import com.easycash.footballleague.data.local.FootballDatabase
import com.easycash.footballleague.data.remote.ApiService
import com.easycash.footballleague.ui.competitions.model.CompetitionsResponse
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService, private val footballDB: FootballDatabase) {

    suspend fun getCompetitions() = apiService.getCompetitions()

    suspend fun getCompetitionInfo(id:Int) = apiService.getCompetitionInfo(id)

    suspend fun getTeams(id:Int) = apiService.getTeams(id)




   suspend fun insertCompetitionsDB(competitionsResponse: CompetitionsResponse) = footballDB.competitionsDao().insert(competitionsResponse)
           fun getCompetitionsCash() = footballDB.competitionsDao().getCompetitionsCash()

    suspend fun insertCompetitionsInfoDB(competitionsInfoResponse: CompetitionsInfoResponse) = footballDB.competitionsInfoDao().insert(competitionsInfoResponse)
            fun getCompetitionsInfoCash(id: Int) = footballDB.competitionsInfoDao().getCompetitionsInfoCash(id)

    suspend fun insertTeamDB(teamResponse: TeamsResponse) = footballDB.teamDao().insert(teamResponse)
            fun getTeamCash(id: Int) = footballDB.teamDao().getTeamCash(id)

}