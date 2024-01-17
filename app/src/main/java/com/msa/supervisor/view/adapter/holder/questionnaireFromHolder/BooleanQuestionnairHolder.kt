package com.msa.supervisor.view.adapter.holder.questionnaireFromHolder

import com.msa.supervisor.databinding.ItemBooleanControlQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.zires.switchsegmentedcontrol.ZiresSwitchSegmentedControl

class BooleanQuestionnairHolder(
    private val binding:ItemBooleanControlQuestionnaireBinding,
    private val questionnaireLineOption: List<QuestionnaireLineOptionEntity>
):QuestionnaireHolder(binding.root) {


    interface Click {
        fun selectItem(item: QuestionnaireLineModel)
    }

    override fun bind(item: QuestionnaireLineModel, position: Int) {
        binding.txtTitle.text=item.title
        setListener(item)
    }

    private fun setListener(item: QuestionnaireLineModel) {
        binding.booleanSwitch.setOnToggleSwitchChangeListener(object :
            ZiresSwitchSegmentedControl.OnSwitchChangeListener {
            override fun onToggleSwitchChangeListener(isChecked: Boolean) {
                // your code
                item.answer = isChecked.toString()
                item.selectedAnswerId =item.uniqueId
            }
        })
    }
}