package com.msa.supervisor.model.data.request

/**
 * create by Ali Soleymani.
 */
data class SyncCustomerCallQuestionnaire(
    var questionnaireUniqueId: String?,
    var answers: List<SyncCustomerQuestionnaireAnswerModel?>
)
