package com.msa.supervisor.view.fragment.pincode

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.response.customer.CustomerPinModel
import com.msa.supervisor.model.data.response.repository.CustomerRepository
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class PinCodeViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : BaseViewModel() {

    val listPinCode: com.msa.supervisor.tool.SingleLiveEvent<List<CustomerPinModel>?>
    by lazy { com.msa.supervisor.tool.SingleLiveEvent<List<CustomerPinModel>?>() }

    fun requestPinCode() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()) {
            val response = callApi2(customerRepository.requestCustomerPinCodes())
            response?.let {
                Log.e(TAG, "${it} ")
             listPinCode.postValue(it)
            }

        }

    }

}