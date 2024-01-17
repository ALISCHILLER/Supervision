package com.msa.supervisor.model.data.response.repository

import com.zar.core.tools.api.apiCall
import com.msa.supervisor.model.api.ApiSupervisor
import com.msa.supervisor.model.data.database.dao.UserLoginDao
import com.msa.supervisor.model.data.request.ConfirmOrderRequest
import com.msa.supervisor.model.data.request.DataConfrimOrderReguest
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
class OrderRepository @Inject constructor(
    private val apiSupervisor: ApiSupervisor
){

    suspend fun requestOrderConfirm(confirmOrderRequest: ConfirmOrderRequest)=
       apiCall { apiSupervisor.requestOrderConfirm(confirmOrderRequest) }


    suspend fun requestSendDataOrderConfirm(dataOrderConfirm: DataConfrimOrderReguest)=
        apiCall { apiSupervisor.sendDataConfrimOrder(dataOrderConfirm) }
}