package com.msa.supervisor.view.fragment.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.response.repository.ListOfBranchesRepository
import com.msa.supervisor.model.data.response.repository.UserRepository
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val listOfBranchesRepository: ListOfBranchesRepository
) : BaseViewModel() {

    val successLiveData: com.msa.supervisor.tool.SingleLiveEvent<Boolean> by lazy { com.msa.supervisor.tool.SingleLiveEvent<Boolean>() }

    //---------------------------------------------------------------------------------------------- userIsEntered
    fun userIsEntered() = userRepository.isEntered()
    //---------------------------------------------------------------------------------------------- userIsEntered

    fun requestSetting() {
        viewModelScope.launch(IO + exceptionHandler()){
                val response = callApi2(listOfBranchesRepository.requestBranches())
                response?.let {
                    Log.e("Login", "requestLogin:${it} ")
                    insertBranches(it)
                    successLiveData.postValue(true)
                }

        }
    }


    private fun insertBranches(items: List<BranchesEntity?>) {
        listOfBranchesRepository.insertBranches(items)
    }
}