package com.hys.hy.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

private val userHome get() = System.getProperty("user.home")
private const val APP_NAME = "HY"

actual fun createHyDatabase(): HyDatabase {
    val parentFolder = File(userHome + "/${APP_NAME}")
    if (!parentFolder.exists()) {
        parentFolder.mkdirs()
    }

    val databasePath = File(parentFolder, DB_FILE_NAME)
    return Room
        .databaseBuilder<HyDatabase>(
            name = databasePath.absolutePath,
        ).setDefaults()
        .setDriver(BundledSQLiteDriver())
        .build()
}
