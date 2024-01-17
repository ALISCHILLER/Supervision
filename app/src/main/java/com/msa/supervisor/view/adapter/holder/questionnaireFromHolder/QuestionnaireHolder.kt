package com.msa.supervisor.view.adapter.holder.questionnaireFromHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel

abstract class QuestionnaireHolder(val view: View): RecyclerView.ViewHolder(view) {

    abstract fun bind(item: QuestionnaireLineModel, position:Int,)
}