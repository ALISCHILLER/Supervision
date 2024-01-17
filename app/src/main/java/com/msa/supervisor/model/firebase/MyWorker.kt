package com.msa.supervisor.model.firebase

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
/**
 * create by Ali Soleymani.
 */
class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d(TAG, "Performing long running task in scheduled job")
        // TODO(developer): add long running task here.
        return Result.success()
    }

    companion object {
        private val TAG = "MyWorker"
    }
}
