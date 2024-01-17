package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("userLogin")
data class UserLoginEntity(
    @PrimaryKey
    val id: String,
    val personnelName: String?,
    val personnelCode: String?,
    val zoneIds: String?
) {
}