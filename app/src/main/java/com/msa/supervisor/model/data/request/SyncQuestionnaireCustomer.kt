package com.msa.supervisor.model.data.request

import java.util.Date
import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class SyncQuestionnaireCustomer(
    val customerUniqueId: String?,
    val callDate: Date? ,
    val callPDate: String?,
    val startTime: Date?,
    val startPTime: String?,
    val endTime: Date?,
    val endPTime: String?,
    val latitude:Double,
    val longitude:Double,
    val receiveDate: Date?,
    val receivePDate: String? ,
    val visitDuration:Int,
    var customerCallQuestionnaires:List<SyncCustomerCallQuestionnaire?>?
) {



}