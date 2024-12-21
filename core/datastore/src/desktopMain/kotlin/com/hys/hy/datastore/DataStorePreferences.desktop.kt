package com.hys.hy.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import java.io.File

private const val APP_NAME = "HY"
private val contextDir: String = System.getProperty("user.home")  // 获取用户的 home 目录
private val preferencesFile =
    File(contextDir + File.separator + APP_NAME + File.separator, SETTINGS_PREFERENCES)

actual fun dataStorePreferences(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>
): DataStore<Preferences> =
    createDataStoreWithDefaults(
        corruptionHandler = corruptionHandler,
        migrations = migrations,
        coroutineScope = coroutineScope,
        path = { preferencesFile.absolutePath }
    )