package com.msa.supervisor.model.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("pinCodeConfirm")
data class PinCodeConfirmEntity(
    @PrimaryKey
    val uniqueId:String,
    val customerId: String?,//key = custoemr
    val pinType: String?,//key = pin
    val pinName: String?,//key = pinname
    val dealerId: String?,//key = user
    val customerName: String?,//key = custoemrName
    val dealerName: String?,//key = userName
    val date: String?,
    val Status: String?,
    val customer_call_order: String
) {
}