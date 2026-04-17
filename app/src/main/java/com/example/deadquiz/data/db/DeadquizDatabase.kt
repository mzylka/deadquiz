package com.example.deadquiz.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class, Score::class], version = 1, exportSchema = false)
abstract class DeadquizDatabase : RoomDatabase() {
    abstract fun itemDAO(): ItemDAO
    abstract fun scoreDAO(): ScoreDAO

    companion object {
        @Volatile
        private var Instance: DeadquizDatabase? = null
        fun getDatabase(context: Context): DeadquizDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DeadquizDatabase::class.java, "deadquiz_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
