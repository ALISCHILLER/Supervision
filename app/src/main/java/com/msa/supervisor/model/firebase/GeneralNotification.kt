package com.msa.supervisor.model.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage
import com.msa.supervisor.R
import com.msa.supervisor.view.activity.MainActivity
/**
 * create by Ali Soleymani.
 */
abstract class GeneralNotification(protected var mContext: Context?, remoteMessage: RemoteMessage) {
    protected var mRemoteMessage: RemoteMessage

    init {
        mRemoteMessage = remoteMessage
        mContext?.let { createNotificationChannel(it) }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    open fun sendNotification() {
        val intent = Intent(mContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent: PendingIntent
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                mContext,
                0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                mContext,
                0, intent,
                PendingIntent.FLAG_ONE_SHOT
            )
        }
        val channelId = mContext?.getString(R.string.default_notification_channel_id)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        if (mRemoteMessage.getNotification() == null) return
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext!!, channelId.toString())
                .setSmallIcon(R.drawable.ic_logo_supervisor_app)
                .setContentTitle(mRemoteMessage.getNotification()!!.getTitle())
                .setContentText(mRemoteMessage.getNotification()!!.getBody())
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(mRemoteMessage.getNotification()!!.getBody())
                )
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        val notificationManager: NotificationManager =
            mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val ZAR_CHANNEL_ID = "ZAR_CHANNEL"
        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager: NotificationManager =
                    context.getSystemService<NotificationManager>(
                        NotificationManager::class.java
                    )
                if (notificationManager.getNotificationChannel(ZAR_CHANNEL_ID) == null) {
                    val channel = NotificationChannel(
                        ZAR_CHANNEL_ID, "Zar Notification", NotificationManager.IMPORTANCE_HIGH
                    )
                    channel.setDescription("Zar Notification")
                    channel.setVibrationPattern(longArrayOf(1000, 1000))
                    channel.enableVibration(true)
                    channel.setLightColor(ContextCompat.getColor(context, R.color.orange))
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }
}