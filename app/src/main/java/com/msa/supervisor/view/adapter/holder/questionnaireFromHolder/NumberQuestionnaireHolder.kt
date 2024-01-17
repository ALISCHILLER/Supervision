package com.msa.supervisor.view.adapter.holder.questionnaireFromHolder

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.msa.supervisor.R
import com.msa.supervisor.databinding.ItemNumberControlQuestionnaireBinding
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.msa.supervisor.tool.customtoast.MsaToast
import com.msa.supervisor.tool.customtoast.MsaToastStyle
import java.lang.Integer.parseInt

class NumberQuestionnaireHolder(
    private val binding: ItemNumberControlQuestionnaireBinding,
    val context:Context
) : QuestionnaireHolder(binding.root) {


    interface Click {
        fun selectItem(item: QuestionnaireLineModel)
    }

    override fun bind(item: QuestionnaireLineModel, position: Int) {
        binding.txtTitle.text = item.title

        setListener(item)
    }

    private fun setListener(item: QuestionnaireLineModel) {

        binding.valueEditText.addTextChangedListener {
            val number = parseInt(it.toString())
            if (item.maxValue > number) {
                if (it.isNullOrEmpty()) {
                    item.answer = it.toString()
                    item.selectedAnswerId = item.uniqueId
                } else {
                    item.answer = it.toString()
                    item.selectedAnswerId = item.uniqueId
                }
            } else {
                MsaToast.darkColorToast(
                    context as Activity,
                    "خطا ☹️",
                    "عدد وارد شده بیشتر ازمقدار مجاز است",
                    MsaToastStyle.WARNING,
                    MsaToast.GRAVITY_BOTTOM,
                    MsaToast.LONG_DURATION,
                    ResourcesCompat.getFont(context, R.font.mitra_bold),
                    context.getColor(R.color.white)
                )
            }

        }
    }

}