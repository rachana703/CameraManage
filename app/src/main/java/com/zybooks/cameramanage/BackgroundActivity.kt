package com.zybooks.cameramanage

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class BackgroundTaskWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        // Intent to listen to the battery changes
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = applicationContext.registerReceiver(null, intentFilter)

        // Get the battery level and scale
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        // Check if the values are valid
        if (level == -1 || scale == -1) {
            Log.e("WorkManager", "Failed to retrieve battery data")
            return Result.failure()
        }

        // Calculate the battery percentage safely
        val batteryPct = if (scale != 0) level.toFloat() / scale * 100 else 0f
        Log.d("WorkManager", "Battery percentage: $batteryPct%")

        return Result.success()
    }
}
