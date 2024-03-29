package com.msa.supervisor.model.data.response.notification
/**
 * create by Ali Soleymani.
 */
data class NotificationModel(
    val lastUpdatePersian : String?,
    val receiverName : String?,
    var isRead : Boolean,
    val message : String?,
    val statusMessage : String?,
    val senderId : Int,
    val senderName : String?,
    val receiverId : Int,
    val messageType : String?,
    val lastUpdate : String?,
    val systemType : String,
    val destinationWebToken : String?,
    val id : Int,
    val status : String?,
    var select : Boolean
)