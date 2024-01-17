package com.msa.supervisor.view.application

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.msa.supervisor.tool.CompanionValues
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */

@HiltAndroidApp
class ZarApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    //---------------------------------------------------------------------------------------------- onCreate
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    //---------------------------------------------------------------------------------------------- onCreate


    //---------------------------------------------------------------------------------------------- getWorkManagerConfiguration
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    //---------------------------------------------------------------------------------------------- getWorkManagerConfiguration


    //---------------------------------------------------------------------------------------------- createNotificationChannel
    private fun createNotificationChannel() {
        val vibrate: LongArray = longArrayOf(1000L, 1000L, 1000L, 1000L, 1000L)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            CompanionValues.channelId,
            CompanionValues.channelName,
            importance
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.enableVibration(true)
        channel.vibrationPattern = vibrate
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
    //---------------------------------------------------------------------------------------------- createNotificationChannel

}