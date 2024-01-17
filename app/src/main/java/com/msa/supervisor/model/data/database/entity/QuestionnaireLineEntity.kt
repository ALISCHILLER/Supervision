package com.msa.supervisor.model.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("questionnaireLine")
data class QuestionnaireLineEntity (
    @PrimaryKey
    val uniqueId: String,
    val questionnaireUniqueId: String?,
    val title: String? = null,
    val questionnaireLineTypeUniqueId: String? ,
    val hasAttachment: Boolean,
    val numberOfOptions: Int,
    val attachmentTypeUniqueId: String?,
    val questionGroupUniqueId: String?,
    var isMandatory: Boolean,
    var isRemoved: Boolean,
    val rowIndex: Int,
    var minValue: Int,
    var maxValue: Int,
){

}