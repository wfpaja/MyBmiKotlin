package com.example.mybmikotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Info::class], version = 1, exportSchema = false)
abstract class InfoDatabase: RoomDatabase() {
    abstract fun infoDao(): InfoDao

    companion object {
        @Volatile
        private var INSTANCE: InfoDatabase? = null
        fun getDatabase(context: Context): InfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InfoDatabase::class.java,
                    "info_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}