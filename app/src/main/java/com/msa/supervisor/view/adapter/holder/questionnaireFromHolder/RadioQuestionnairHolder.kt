package com.msa.supervisor.view.adapter.holder.questionnaireFromHolder

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.msa.supervisor.R
import com.msa.supervisor.databinding.ItemRadioControlQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel

class RadioQuestionnairHolder(
    private val binding: ItemRadioControlQuestionnaireBinding,
    private val questionnaireLineOption: List<QuestionnaireLineOptionEntity>,
    private val typeCheck: Boolean
) : QuestionnaireHolder(binding.root) {

    interface Click {
        fun selectItem(item: QuestionnaireLineModel)
    }

    override fun bind(item: QuestionnaireLineModel, position: Int) {
        binding.txtTitle.text = item.title
        if (typeCheck)
            setListener(item)
        else
            setListenerRadioButton(item)
    }

    private fun setListener(item: QuestionnaireLineModel) {
        if (binding.linearLayoutAnswer.childCount > 0)
            return
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(10, 10, 10, 10)
        val typeface = ResourcesCompat.getFont(binding.root.context, R.font.kalameh_regular)
        questionnaireLineOption?.let { options ->
            for (i in options.indices) {
                options[i].takeIf { it.questionnaireLineUniqueId == item.uniqueId }?.let {
                    val checkBox = CheckBox(binding.root.context).apply {
                        textSize = 12f
                        setTextColor(binding.root.context.getColor(R.color.black))
                        layoutParams = param
                        text = options[i].title
                        layoutDirection = LinearLayout.LAYOUT_DIRECTION_RTL
                        setTypeface(typeface)
                        setOnClickListener {
                            if (isChecked) {
                                item.selectedAnswerId = options[i].questionnaireLineUniqueId
                                item.selectedAnswersId?.add(options[i].uniqueId)
                                item.answer = options[i].title

                            } else {
                                item.selectedAnswersId?.remove(options[i].uniqueId)
                                item.selectedAnswerId = options[i].questionnaireLineUniqueId
                                item.answer = options[i].title
                            }
                        }
                    }
                    binding.linearLayoutAnswer.addView(checkBox, param)
                }
            }
        }
    }

        private fun setListenerRadioButton(item: QuestionnaireLineModel) {
            if (binding.linearLayoutAnswer.childCount > 0)
                return
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(10, 10, 10, 10)
            val typeface = ResourcesCompat.getFont(binding.root.context, R.font.kalameh_regular)
            val radioGroup = RadioGroup(binding.root.context)



            questionnaireLineOption?.let { options ->
                for (i in options.indices) {
                    if (options[i].questionnaireLineUniqueId == item.uniqueId) {
                        val radioButton = RadioButton(binding.root.context).apply {
                            textSize = 12f
                            setTextColor(
                                ContextCompat.getColor(
                                    binding.root.context,
                                    R.color.black
                                )
                            )
                            layoutParams = param
                            text = options[i].title
                            layoutDirection = View.LAYOUT_DIRECTION_RTL
                            setTypeface(typeface)
                            setOnClickListener {
                                item.selectedAnswerId = options[i].questionnaireLineUniqueId
                                item.selectedAnswersId?.add(options[i].uniqueId)
                                item.answer = options[i].title
                            }
                        }
                        radioGroup.addView(radioButton, param)
                    }
                }
            }

            // Set the listener for the radio group to update the answer
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                // Get the selected radio button from the group
                val radioButton = group.findViewById<RadioButton>(checkedId)
                // Update the answer with the selected option
                updateAnswer(radioButton.text.toString())
            }
            binding.linearLayoutAnswer.addView(radioGroup)

        }


    private fun updateAnswer(selectedOption: String) {
        // Update the answer with the selected option
    }

}