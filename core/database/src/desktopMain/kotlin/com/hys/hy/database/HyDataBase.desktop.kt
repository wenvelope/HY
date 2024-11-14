package com.hys.hy.database

import androidx.room.Room
import java.io.File

private val userHome get() = System.getProperty("user.home")
private const val APP_NAME = "ComposeApp"

actual fun createHyDatabase(): HyDatabase {

    val parentFolder = File(userHome + "/${APP_NAME}")
    if (!parentFolder.exists()) {
        parentFolder.mkdirs()
    }

    val databasePath = File(parentFolder, dbFileName)
    return Room.databaseBuilder<HyDatabase>(
        name = databasePath.absolutePath,
    ).setDefaults().build()
}