package com.msa.supervisor.model.data.response.pincode
/**
 * create by Ali Soleymani.
 */
data class PinCodeConfirm(
    val customerId: String?,//key = custoemr
    val pinType: String?,//key = pin
    val pinName: String?,//key = pinname
    val dealerId: String?,//key = user
    val customerName: String?,//key = custoemrName
    val dealerName: String?,//key = userName
    val date: String?,
    val Status: String?,
    val customer_call_order: String
)
