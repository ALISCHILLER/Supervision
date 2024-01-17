package com.msa.supervisor.view.adapter.holder.questionnaireFromHolder

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemTextControlQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel

class TextQuestionnaireHolder(
    private val binding: ItemTextControlQuestionnaireBinding,
) : QuestionnaireHolder(binding.root) {

    interface Click {
        fun selectItem(item: QuestionnaireLineModel)
    }

    override fun bind(item: QuestionnaireLineModel, position: Int) {
        binding.txtTitle.text=item.title
        setListener(item)
    }

    private fun setListener(item: QuestionnaireLineModel) {

        binding.valueEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // این تابع قبل از تغییر متن فراخوانی می‌شود و در اینجا کاری انجام نمی‌دهیم.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // این تابع هنگامی که متن داخل EditText تغییر می‌کند، فراخوانی می‌شود.

                if (s.isNullOrEmpty()) {
                    // اگر متن خالی یا null باشد:
                    item.answer = null
                    item.selectedAnswerId = item.uniqueId
                } else {
                    // اگر متن خالی نباشد:
                    item.answer = s.toString()
                    item.selectedAnswerId =item.uniqueId
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // این تابع بعد از تغییر متن فراخوانی می‌شود و در اینجا کاری انجام نمی‌دهیم.
            }
        })



    }
}
