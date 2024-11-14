package com.hys.hy.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.hys.hy.database.dao.TaskDao
import com.hys.hy.database.entities.Task
import com.hys.hy.database.typeConverter.DateConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
@ConstructedBy(HyDatabaseConstructor::class)
abstract class HyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao  // 获取 TaskDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object HyDatabaseConstructor : RoomDatabaseConstructor<HyDatabase>

const val dbFileName = "HYApp.db"

expect fun createHyDatabase(): HyDatabase

fun <T : RoomDatabase> RoomDatabase.Builder<T>.setDefaults(): RoomDatabase.Builder<T> = this.apply {
    setDriver(BundledSQLiteDriver())
    fallbackToDestructiveMigration(true)
    setQueryCoroutineContext(Dispatchers.IO)
}

