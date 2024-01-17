package com.msa.supervisor.view.adapter.recycler.questionnaireFrom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msa.supervisor.databinding.ItemBooleanControlQuestionnaireBinding
import com.msa.supervisor.databinding.ItemNumberControlQuestionnaireBinding
import com.msa.supervisor.databinding.ItemRadioControlQuestionnaireBinding
import com.msa.supervisor.databinding.ItemTextControlQuestionnaireBinding
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.response.questionnaire.QuestionnaireLineModel
import com.msa.supervisor.view.adapter.holder.questionnaireFromHolder.BooleanQuestionnairHolder
import com.msa.supervisor.view.adapter.holder.questionnaireFromHolder.NumberQuestionnaireHolder
import com.msa.supervisor.view.adapter.holder.questionnaireFromHolder.QuestionnaireHolder
import com.msa.supervisor.view.adapter.holder.questionnaireFromHolder.RadioQuestionnairHolder
import com.msa.supervisor.view.adapter.holder.questionnaireFromHolder.TextQuestionnaireHolder
import java.util.UUID

/**
 * Created by a-soleimani.
 */
class QuestionnaireFromAdapter(
    private val questions: List<QuestionnaireLineModel>,
    private val questionnaireLineOption: List<QuestionnaireLineOptionEntity>,
    val context: Context
    ) : RecyclerView.Adapter<QuestionnaireHolder>() {

    private var inflater: LayoutInflater? = null
    val yesnoType = "c50e74ef-3cca-42ed-acad-867965358287"
    val textType ="66e7eff8-fd82-4a1d-9054-637ac1cb811a"
    val radioType ="75d0045a-1287-4c49-9550-b6308e5ac1aa"
    val selectType = "e5435961-a64c-4075-9509-2de55df29154"
    val numberType ="718bc877-6085-4326-8d23-df09c46365e1"
    val priorityType = "d379f9a5-741c-4ab1-b2e3-f4534bfecb2e"
    val epollType = "b61c476b-a97e-43b6-9746-2b029e75d786"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionnaireHolder {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0-> TextQuestionnaireHolder(
                ItemTextControlQuestionnaireBinding.inflate(inflater!!,parent,false)
            )
            1->NumberQuestionnaireHolder(
                ItemNumberControlQuestionnaireBinding.inflate(inflater!!,parent,false),
                context
            )
            2->BooleanQuestionnairHolder(
                ItemBooleanControlQuestionnaireBinding.inflate(inflater!!,parent,false),
                questionnaireLineOption
            )
            3->RadioQuestionnairHolder(
                ItemRadioControlQuestionnaireBinding.inflate(inflater!!,parent,false),
                questionnaireLineOption,
                false
            )
            4->RadioQuestionnairHolder(
                ItemRadioControlQuestionnaireBinding.inflate(inflater!!,parent,false),
                questionnaireLineOption,
                true
            )
            5->RadioQuestionnairHolder(
                ItemRadioControlQuestionnaireBinding.inflate(inflater!!,parent,false),
                questionnaireLineOption,
                true
            )
            else -> {
                TextQuestionnaireHolder(
                    ItemTextControlQuestionnaireBinding.inflate(inflater!!,parent,false))
            }
        }
    }

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: QuestionnaireHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (questions[position].questionnaireLineTypeUniqueId) {
            textType  -> 0
            numberType->1
            yesnoType -> 2
            radioType -> 3
            selectType -> 4
            priorityType -> 5
            else -> {
                0
            }
        }
    }
}