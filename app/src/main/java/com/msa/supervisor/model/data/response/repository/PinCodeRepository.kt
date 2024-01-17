package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.PinCodeConfirmDao
import com.msa.supervisor.model.data.request.PinCodeApproveReguestModel
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class PinCodeRepository @Inject constructor(
    private val pinCodeConfirmDao: PinCodeConfirmDao,
    private val api:ApiSupervisor
) {


    fun getLIstPincodeConfirm()=pinCodeConfirmDao.getPinCodeConfirm()


    suspend fun requestSendPinrapprove(pinCodeApproveReguest: PinCodeApproveReguestModel)=
        apiCall {api.requestSendPinrapprove(pinCodeApproveReguest)}

}