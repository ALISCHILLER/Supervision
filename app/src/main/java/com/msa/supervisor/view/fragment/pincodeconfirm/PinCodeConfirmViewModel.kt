package com.msa.supervisor.view.fragment.pincodeconfirm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity
import com.msa.supervisor.model.data.request.PinCodeApproveReguestModel
import com.msa.supervisor.model.data.response.repository.PinCodeRepository
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class PinCodeConfirmViewModel @Inject constructor(
    private val pinCodeRepository: PinCodeRepository
) : BaseViewModel() {
    val pincodesConfirmLive: MutableLiveData<List<PinCodeConfirmEntity>>
            by lazy { MutableLiveData<List<PinCodeConfirmEntity>>() }

    val sendPinCode: MutableLiveData<String>
            by lazy { MutableLiveData<String>() }

    fun getListPinCodeConfirm() {
        viewModelScope.launch(IO + exceptionHandler()) {
            val pinCodesConfirm = pinCodeRepository.getLIstPincodeConfirm()
            pincodesConfirmLive.postValue(pinCodesConfirm)
        }
    }


    fun requestrequestPinCodeApprove(pinCodeConfirmEntity: PinCodeConfirmEntity) {
        viewModelScope.launch(IO + exceptionHandler()) {
            val item = PinCodeApproveReguestModel(
                CustomerId = pinCodeConfirmEntity.customerId,
                DealerCode = pinCodeConfirmEntity.dealerId,
                PinType = pinCodeConfirmEntity.pinType,
                DealerId = pinCodeConfirmEntity.dealerId,
                CustomerCallOrderId = pinCodeConfirmEntity.customer_call_order
            )
            val response = pinCodeRepository.requestSendPinrapprove(item)
            response.let {
                Log.e(TAG, "requestrequestPinCodeApprove: $it")
                sendPinCode.postValue(it.toString())
            }
        }
    }

}