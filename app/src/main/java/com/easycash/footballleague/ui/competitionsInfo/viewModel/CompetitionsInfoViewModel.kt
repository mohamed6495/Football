package com.easycash.footballleague.ui.competitionsInfo.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easycash.footballleague.data.repository.Repository
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CompetitionsInfoViewModel  @Inject constructor(private val apiHelper: Repository)  : BaseViewModel() {
    val competitionsResponseLiveData: MutableLiveData<Resource<CompetitionsInfoResponse>> = MutableLiveData()
    fun getCompetitionInfo(id:Int){
        competitionsResponseLiveData.postValue(Resource.Loading())
        launch {
            apiHelper.getCompetitionInfo(id).let { response ->
                    competitionsResponseLiveData.postValue(handleResponse(response))
            }
        }
    }
    private fun handleResponse(response: Response<CompetitionsInfoResponse>): Resource<CompetitionsInfoResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                insertCompetitions(resultResponse)
                return Resource.Success(resultResponse)
            }
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        return Resource.Error(jObjError.getString("message"))
    }

    private fun insertCompetitions(competitionsInfoResponse: CompetitionsInfoResponse) = viewModelScope.launch {
        apiHelper.insertCompetitionsInfoDB(competitionsInfoResponse)
    }

    fun getCompetitionsCash(id: Int): LiveData<CompetitionsInfoResponse> = apiHelper.getCompetitionsInfoCash(id)

}
