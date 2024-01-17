package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("controlValidator")
data class ControlValidatorEntity(
    @PrimaryKey
    var uniqueId: String,
    var name: String?,
    var message: String?,
    var min: Int,
    var max: Int,
)