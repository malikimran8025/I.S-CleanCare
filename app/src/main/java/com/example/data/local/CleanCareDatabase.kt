package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookingEntity::class, ReviewEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CleanCareDatabase : RoomDatabase() {
    abstract fun cleanCareDao(): CleanCareDao

    companion object {
        @Volatile
        private var INSTANCE: CleanCareDatabase? = null

        fun getDatabase(context: Context): CleanCareDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CleanCareDatabase::class.java,
                    "cleancare_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
