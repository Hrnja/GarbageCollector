package com.example.garbagecollector.ORMDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GCUsers::class), version = 1, exportSchema = false)
abstract class GCDatabase : RoomDatabase(){
    abstract fun getDao(): GCDao

    companion object {
        @Volatile
        private var INSTANCE : GCDatabase? = null
        fun getDatabase(context: Context) : GCDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GCDatabase::class.java,
                    "Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}