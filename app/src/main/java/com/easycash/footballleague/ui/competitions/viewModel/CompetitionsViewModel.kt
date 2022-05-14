package com.easycash.footballleague.ui.competitions.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.easycash.footballleague.data.repository.Repository
import com.easycash.footballleague.ui.competitions.model.CompetitionsResponse
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CompetitionsViewModel  @Inject constructor(private val apiHelper: Repository)  : BaseViewModel() {
    val competitionsResponseLiveData: MutableLiveData<Resource<CompetitionsResponse>> = MutableLiveData()
    fun getCompetitions()  {
        competitionsResponseLiveData.postValue(Resource.Loading())
        launch {
            apiHelper.getCompetitions().let { response ->
                    competitionsResponseLiveData.postValue(handleResponse(response))
            }
        }
    }

    private fun handleResponse(response: Response<CompetitionsResponse>): Resource<CompetitionsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                insertCompetitions(resultResponse)
                return Resource.Success(resultResponse)
            }
        }
        val jObjError = JSONObject(response.errorBody()!!.string())
        return Resource.Error(jObjError.getString("message"))
    }

    private fun insertCompetitions(CompetitionsResponse: CompetitionsResponse) = viewModelScope.launch {
        apiHelper.insertCompetitionsDB(CompetitionsResponse)
    }

    fun getCompetitionsCash(): LiveData<CompetitionsResponse> = apiHelper.getCompetitionsCash()

}
