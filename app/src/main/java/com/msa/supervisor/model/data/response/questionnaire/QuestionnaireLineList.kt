package com.msa.supervisor.model.data.response.questionnaire

import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
/**
 * create by Ali Soleymani.
 */
data class QuestionnaireLineList(
    val questionnaireLines: List<QuestionnaireLineEntity>? = null,
)
