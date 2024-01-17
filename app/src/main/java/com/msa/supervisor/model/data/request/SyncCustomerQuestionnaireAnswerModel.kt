package com.msa.supervisor.model.data.request

/**
 * create by Ali Soleymani.
 */

data class SyncCustomerQuestionnaireAnswerModel(
    var uniqueId: String?,

    var customerCallQuestionnaireUniqueId: String?,

    var questionnaireLineUniqueId: String?,

    var answer: String?,

    var options: String?,

    var hasAttachments: Boolean
) {

}
