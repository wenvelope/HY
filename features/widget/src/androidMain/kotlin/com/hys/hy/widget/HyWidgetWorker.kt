package com.hys.hy.widget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class HyWidgetWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        HyAppWidget.updateWidget()
        return Result.success()
    }
}