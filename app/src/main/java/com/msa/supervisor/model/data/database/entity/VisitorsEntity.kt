package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("visitors")
data class VisitorsEntity(
    @PrimaryKey
    val personnelUniqueId: String,
    val personnelCode: String?,
    val personnelName: String?,
    val personnelCodeAndName: String?,
){
}