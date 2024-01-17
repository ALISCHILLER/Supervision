package com.msa.supervisor.model.data.response.questionnaire
/**
 * create by Ali Soleymani.
 */
data class ControlValidatorModel (
    var uniqueId: String,
    var name: String?,
    var message: String?,
    var min: Int=0,
    var max: Int=0
)