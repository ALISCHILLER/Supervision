package com.msa.supervisor.tool.signalr

import com.msa.supervisor.model.data.response.notification.NotificationModel


/**
 * create by Ali Soleymani.
 */

interface RemoteSignalREmitter {

    fun onConnectToSignalR(){}
    fun onErrorConnectToSignalR(){}
    fun onReConnectToSignalR(){}
    fun onReceiveMessage(user : String, message : NotificationModel){}
    fun onGetPoint(lat : String, lng : String, visitorId:String){}
    fun onPreviousStationReached(message : String){}
}