package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "customer")
data class CustomerEntity(
    @PrimaryKey
    val uniqueId: UUID,
    val customerCode:String?,
    val customerName:String?,
    val address:String?,
    val phone:String?,
    val storeName:String?,
    val nationalCode:String?,
    val mobile:String?,
    val longitude:Double,
    val latitude:Double,
    val dealerId: UUID?,
    val dealerName:String?,
    val customerLevelId: UUID?,
    val customerLevel:String?,
    val customerActivityId: UUID?,
    val customerCategory:String?,
    val customerCategoryId: UUID?,
    val cityId: UUID?,
    val stateId: UUID?,
    val centerId: UUID?
)
