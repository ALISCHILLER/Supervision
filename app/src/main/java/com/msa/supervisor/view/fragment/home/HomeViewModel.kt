package com.msa.supervisor.view.fragment.home

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.database.entity.VisitorsEntity
import com.msa.supervisor.model.data.request.VisitorVisitInfoRequest
import com.msa.supervisor.model.data.response.repository.ListOfBranchesRepository
import com.msa.supervisor.model.data.response.repository.VisitorRepository
import com.msa.supervisor.model.data.response.visitor.VisitorVisitInfoModel
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * create by m-latifi on 4/15/2023
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiSupervisor: ApiSupervisor,
    private val visitorRepository: VisitorRepository,
    private val listOfBranchesRepository: ListOfBranchesRepository,
) : BaseViewModel() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val visitInfoModel: MutableLiveData<List<VisitorVisitInfoModel>> by
    lazy { MutableLiveData<List<VisitorVisitInfoModel>>() }
    val user: com.msa.supervisor.tool.SingleLiveEvent<UserLoginEntity?> by lazy { com.msa.supervisor.tool.SingleLiveEvent<UserLoginEntity?>() }
    fun requestVisitorInfo(){
        viewModelScope.launch(IO + exceptionHandler()){
            val userId = sharedPreferences.getString(CompanionValues.userId, "")
            val visitorId=visitorRepository.getVisitorsId()
            val visitorInfo=VisitorVisitInfoRequest(userId,visitorId)
            val response=callApi2(apiSupervisor.getVisitorsVisitInfo(visitorInfo))
            response?.let {
                Log.e(TAG, "$it ")
                visitInfoModel.postValue(it)
            }
        }
    }
    fun getUser(){
        viewModelScope.launch(IO + exceptionHandler()) {
            val userLogin = listOfBranchesRepository.getUserLogin()
            user.postValue(userLogin)
        }
    }
    fun getVisitors(): List<VisitorsEntity> {
        return visitorRepository.getVisitors()
    }

}