package com.msa.supervisor.model.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.msa.supervisor.R
import com.msa.supervisor.tool.CompanionValues
import com.msa.supervisor.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
/**
 * create by Ali Soleymani.
 */
@AndroidEntryPoint
class MyFirebaseMessagingService :FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MyFirebaseMessagingService", "From: ${message.from}")
        val map: Map<String, String> = message.getData()
        val type = map["type"]
        if (!type.isNullOrEmpty()) {
            when (type) {
                "request_pin" -> RequestPin(this, message).sendNotification()
                else ->
                    message.notification?.let {
                        Log.d("MyFirebaseMessagingService", "Message Notification Body: ${it.body}")
                        Log.d("MyFirebaseMessagingService", "Message Notification Body: ${it.title}")
                        it.body?.let { it1 -> it.title?.let { it2 -> sendNotification(it1, it2) } }
                    }
            }
        }

        if (message.data.isNotEmpty()) {
            Log.d("MyFirebaseMessagingService", "Message data payload: ${message.data}")
            if (isLongRunningJob()) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
            // Check if data needs to be processed by long running job
        }


    }

    private fun isLongRunningJob() = true
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseMessaging.getInstance().subscribeToTopic(packageName)
        Log.d("MyFirebaseMessagingService", "onNewToken: token = $token")
        saveToken(token)

    }

    fun saveToken(token: String){
        val oldtoken = sharedPreferences.getString(CompanionValues.tokenFirebase, "")
        oldtoken?.takeIf { it.isEmpty() }?.let {
            sharedPreferences.edit().putString(CompanionValues.tokenFirebase, token)
            Log.d("MyFirebaseMessagingService", "onNewToken: token = $token")
        }?:run {
            token.takeIf { it != oldtoken }?.run {
                sharedPreferences.edit().putString(CompanionValues.tokenFirebase, token)
                Log.d("MyFirebaseMessagingService", "oldtoken: token = $token")
            }
        }

    }

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }
    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d("MyFirebaseMessagingService", "Short lived task is done.")
    }


        fun refreshToken(
            oldtoken:String,
            calback: Callback
        ) {
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.d("MyFirebaseMessagingService", "Refreshed token: $token")
                    if(oldtoken != token)
                        calback.onSuccess(token)
                    calback.onSuccess()
                }
        }


    interface Callback {
        fun onSuccess()
        fun onSuccess(token:String)
        fun onError(error: String?)
    }



    private fun sendNotification(messageBody: String,title:String) {
        val requestCode = 0
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_logo_supervisor_app)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}