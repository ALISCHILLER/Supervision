package com.msa.supervisor.view.fragment.customerconfirmation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.response.repository.CustomerapprovalRepository
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
/**
 * Created by  Ali Soleymani.
 */
@HiltViewModel
class CustomerconfirmationViewModel@Inject constructor(
    private val customerapprovalRepository: CustomerapprovalRepository
) : BaseViewModel() {

    val customerapprovalLiveDate =
        com.msa.supervisor.tool.SingleLiveEvent<List<CustomerapprovalEntity>>()
    @Inject
    lateinit var sharedPreferences: SharedPreferences


      fun requestCustomerApproval(){
          viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
              val userId = sharedPreferences.getString(CompanionValues.userId, "bd954e10-c055-4582-a3d7-13223b6994fa")
              userId?.let {
                  val response = callApi2(customerapprovalRepository.requestCustomerapproval(it))
                  response?.let { it1 ->
                      Log.e("requestCustomerApproval", "requestLogin:${it1} ")
                      customerapprovalRepository.insertCustomerApproval(it1)
                      customerapprovalLiveDate.postValue(it1)
                  }
              }
          }
    }
    fun requestSendCustomerapproval(customerId:UUID,state:Boolean){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
            val response=callApi2(customerapprovalRepository.requestSendCustomerapproval(customerId,state))
            response.let {
                Log.e("requestCustomerApproval", "requestLogin:${it} ")
                requestCustomerApproval()
            }
        }
    }

    fun getItem() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
            val customerapproval = customerapprovalRepository.getCustomerApproval()
            customerapproval.let {
                customerapprovalLiveDate.postValue(customerapproval)
            }
        }
    }


    fun searchAllFields(searchQuery:String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
            val searchValue = "%$searchQuery%"
            val query = SimpleSQLiteQuery("SELECT * FROM customerapproval WHERE uniqueId LIKE '%$searchValue%' " +
                    "OR customerName LIKE '%$searchValue%'" +
                    "OR customerCode LIKE '%$searchValue%'" +
                    "OR phone LIKE '%$searchValue%' " +
                    "OR storeName LIKE '%$searchValue%' " +
                    "OR mobile LIKE '%$searchValue%'" +
                    "OR nationalCode LIKE '%$searchValue%'" +
                    "OR customerLevel LIKE '%$searchValue%' " +
                    "OR customerActivity LIKE '%$searchValue%' " +
                    "OR customerCategory LIKE '%$searchValue%'" +
                    "OR dealerName LIKE '%$searchValue%' " +
                    "OR newStoreName LIKE '%$searchValue%' " +
                    "OR newAddress LIKE '%$searchValue%'" +
                    "OR newPhone LIKE '%$searchValue%'" +
                    "OR newMobile LIKE '%$searchValue%' " +
                    "OR newPostCode LIKE '%$searchValue%'" +
                    "OR newCustomerLevelName LIKE '%$searchValue%' " +
                    "OR newCustomerActivityName LIKE '%$searchValue%' " +
                    "OR newCustomerCategoryName LIKE '%$searchValue%' " +
                    "OR newNationalCode LIKE '%$searchValue%'" +
                    "OR newEconomicCode LIKE '%$searchValue%'" +
                    "OR postCode LIKE '%$searchValue%'" +
                    "OR economicCode LIKE '%$searchValue%'" +
                    "OR codeNaghs LIKE'%$searchValue%' " +
                    "OR newCodeNaghdh LIKE '%$searchValue%'")
            val customerapproval = customerapprovalRepository.searchAllFields(query)
            customerapproval.let {
                customerapprovalLiveDate.postValue(customerapproval)
            }
        }
    }

}