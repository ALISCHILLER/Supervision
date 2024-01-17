package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity("questionnaireLineOption")
data class QuestionnaireLineOptionEntity (
    @PrimaryKey
    var uniqueId: String,
    var questionnaireLineUniqueId: String?,
    var title: String? ,
    var rowIndex: Int,
    var isRemoved: Boolean,
    var questionGroupUniqueId: String?,
    var answerAttachmentUniqueId: String?
)