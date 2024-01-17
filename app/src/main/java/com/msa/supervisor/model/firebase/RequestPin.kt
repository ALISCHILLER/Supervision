package com.msa.supervisor.model.firebase

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage
import com.msa.supervisor.R
import com.msa.supervisor.view.activity.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
/**
 * create by Ali Soleymani.
 */
class RequestPin(context: Context?, remoteMessage: RemoteMessage) :
    GeneralNotification(context, remoteMessage) {
    private val _customerId //key = custoemr
            : UUID
    private val _pinType //key = pin
            : String?
    private val _dealerId //key = user
            : UUID
    private val _customerName //key = customerName
            : String?
    private val _dealerName //key = userName
            : String?
    private var _customer_call_order: UUID? = null
    private var isValid = true

    init {
        val map: Map<String, String> = remoteMessage.data
        val customerId = map["customer"]
        val pinType = map["pin"]
        val dealerId = map["user"]
        val customerName = map["customerName"]
        val delaerName = map["userName"]
        val customercallorder = map["customer_call_order"]
        if (customerId.isNullOrEmpty()) {
            isValid = false
        }
        if (pinType.isNullOrEmpty()) {
            isValid = false
        }
        if (dealerId.isNullOrEmpty()) {
            isValid = false
        }
        if (customerName.isNullOrEmpty()) {
            isValid = false
        }
        if (delaerName.isNullOrEmpty()) {
            isValid = false
        }
        if (customercallorder.isNullOrEmpty()
            && pinType != "pin4"
        ) {
            isValid = false
        }
        _customerId = UUID.fromString(customerId)
        _pinType = pinType
        _dealerId = UUID.fromString(dealerId)
        _customerName = customerName
        _dealerName = delaerName
        if (customercallorder != null) _customer_call_order = UUID.fromString(customercallorder)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun sendNotification() {
        if (!isValid) return
        //pin type string name
        var pinName = ""
        when (_pinType) {
            "pin1" -> pinName = mContext!!.getString(R.string.pin1)
            "pin2" -> pinName = mContext!!.getString(R.string.pin2)
            "pin3" -> pinName = mContext!!.getString(R.string.pin3)
            "pin4" -> pinName = mContext!!.getString(R.string.pin4)
        }

        //save request to db
        val now = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = simpleDateFormat.format(now)




        val intent = Intent(mContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("pin_code", "pin_layout")
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                mContext,
                0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

        //send notif
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext!!, ZAR_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_supervisor_app)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(mContext!!, R.color.primaryColor))
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setContentTitle("زر ماکارون")
                .setContentText(
                    mContext!!.getString(
                        R.string.request_pin,
                        _dealerName,
                        pinName,
                        _customerName
                    )
                )
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            mContext!!.getString(
                                R.string.request_pin,
                                _dealerName,
                                pinName,
                                _customerName
                            )
                        )
                )
                .setContentIntent(pendingIntent)
        val notificationManager: NotificationManager =
            mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifId = _dealerId.toString()
            .replace("-".toRegex(), "")
            .substring(0, 4).toInt(16)
        notificationManager.notify(notifId, mBuilder.build())
    }
}