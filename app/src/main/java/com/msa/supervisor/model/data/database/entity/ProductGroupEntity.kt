package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID


@Entity("productGroup")
data class ProductGroupEntity(
    @PrimaryKey
    var uniqueId:String,
    val productGroupId: String?,
    val productGroupName:String?,
    val orderOf:Int,
    val rowIndex:Int,
){
}