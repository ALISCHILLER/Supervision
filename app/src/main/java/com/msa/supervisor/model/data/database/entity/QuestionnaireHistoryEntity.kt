package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID



@Entity("questionnaireHistory")
data class QuestionnaireHistoryEntity(
    @PrimaryKey
    var uniqueId:String,
    var questionnaireId: String?,
    var customerId: String?,
)
