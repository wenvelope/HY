package com.hys.hy.database

import android.content.Context
import androidx.room.Room
import org.koin.mp.KoinPlatform

actual fun createHyDatabase(): HyDatabase {
    val appContext = KoinPlatform.getKoin().get<Context>()
    val dbFile = appContext.getDatabasePath(DB_FILE_NAME)
    return Room
        .databaseBuilder<HyDatabase>(
            KoinPlatform.getKoin().get(),
            name = dbFile.absolutePath,
        ).setDefaults()
        .build()
}
