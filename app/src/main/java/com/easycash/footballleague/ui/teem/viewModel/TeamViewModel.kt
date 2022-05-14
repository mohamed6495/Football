package com.easycash.footballleague.ui.teem.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easycash.footballleague.data.repository.Repository
import com.easycash.footballleague.ui.teem.model.TeamsResponse
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class TeamViewModel  @Inject constructor(private val apiHelper: Repository)  : BaseViewModel() {

    val teamResponseLiveData: MutableLiveData<Resource<TeamsResponse>> = MutableLiveData()

    fun getTeam(id:Int)  {
        teamResponseLiveData.postValue(Resource.Loading())
        launch {
            apiHelper.getTeams(id).let { response ->

                    teamResponseLiveData.postValue(handleResponse(response,id))
            }
        }
    }

    private fun handleResponse(response: Response<TeamsResponse>,id:Int): Resource<TeamsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                resultResponse.id=id
                insertTeams(resultResponse)
                return Resource.Success(resultResponse)
            }
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        return Resource.Error(jObjError.getString("message"))
    }

    private fun insertTeams(teamResponse: TeamsResponse) = viewModelScope.launch {
        apiHelper.insertTeamDB(teamResponse)
    }

    fun getTeamsCash(id: Int): LiveData<TeamsResponse> = apiHelper.getTeamCash(id)

}
