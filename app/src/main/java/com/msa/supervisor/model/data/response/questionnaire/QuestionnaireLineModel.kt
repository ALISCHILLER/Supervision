package com.msa.supervisor.model.data.response.questionnaire

import java.util.UUID
/**
 * create by Ali Soleymani.
 */
data class QuestionnaireLineModel(
    var uniqueId:String,
    var questionnaireUniqueId: String?,
    var title: String?,
    var questionnaireLineTypeUniqueId: String?,
    var hasAttachment: Boolean ,
    var numberOfOptions: Int,
    var attachmentTypeUniqueId: String?,
    var questionGroupUniqueId: String?,
    var minValue: Int,
    var maxValue: Int,
    var rowIndex: Int,
    var isMandatory: Boolean,
    var isRemoved: Boolean,
    var validators: List<ControlValidatorModel>?,
    var questionnaireLineOptions: List<QuestionnaireLineOptionModel>?
) {
    var answer: String? = null
    var selectedAnswerId: String? = null
    var selectedAnswersId: MutableList<String>? = null
}