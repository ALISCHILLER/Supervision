package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("branches")
data class BranchesEntity(

    val uniqueId: String?,
    val scope: String?,
    val name: String?,
    @PrimaryKey
    val value: String,
    val title: String?,
    val dateCulture: String?,
){
}