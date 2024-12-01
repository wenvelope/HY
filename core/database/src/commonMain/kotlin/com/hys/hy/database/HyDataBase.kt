package com.hys.hy.database

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.hys.hy.database.dao.TaskCategoryDao
import com.hys.hy.database.dao.TaskDao
import com.hys.hy.database.entities.TaskCategoryTable
import com.hys.hy.database.entities.TaskTable
import com.hys.hy.database.typeConverter.DateConverter
import com.hys.hy.database.typeConverter.TimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        TaskTable::class,
        TaskCategoryTable::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
@TypeConverters(DateConverter::class, TimeConverter::class)
@ConstructedBy(HyDatabaseConstructor::class)
abstract class HyDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao  // 获取 TaskDao
    abstract fun TaskCategoryDao(): TaskCategoryDao // 获取 TaskCategoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
internal expect object HyDatabaseConstructor : RoomDatabaseConstructor<HyDatabase>

const val dbFileName = "HYApp.db"

expect fun createHyDatabase(): HyDatabase

fun <T : RoomDatabase> RoomDatabase.Builder<T>.setDefaults(): RoomDatabase.Builder<T> = this.apply {
    fallbackToDestructiveMigration(true)
    setQueryCoroutineContext(Dispatchers.IO)
}

