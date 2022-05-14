package com.easycash.footballleague.data.remote


import com.easycash.footballleague.ui.competitions.model.CompetitionsResponse
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import retrofit2.http.*
import retrofit2.Response

interface ApiService {

    @GET("/v2/competitions")
    suspend fun getCompetitions():  Response<CompetitionsResponse>

    @GET("/v2/competitions/{id}")
    suspend fun getCompetitionInfo(@Path("id") id: Int):  Response<CompetitionsInfoResponse>

    @GET("/v2/competitions/{id}/teams")
    suspend fun getTeams(@Path("id") id: Int):  Response<TeamsResponse>


}