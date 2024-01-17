package com.msa.supervisor.model.data.request

import java.util.Date
/**
 * create by Ali Soleymani.
 */

data class SyncQuestionnaire(

    private val tourUniqueId: String?,
    private val sendDate: Date?,
    private val apkVersion: String?,
    private var customerCalls: List<SyncQuestionnaireCustomer?>
) {




}