package com.msa.supervisor.model.data.response.questionnaire

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class QuestionnaireLineOptionModel (
    var uniqueId: String,
    var questionnaireLineUniqueId: String?,
    var title: String?,
    var rowIndex: Int,
    var isRemoved: Boolean,
    var questionGroupUniqueId: String?,
    var answerAttachmentUniqueId: String?
)