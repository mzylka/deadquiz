package com.example.deadquiz.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(score: Score)

    @Query("SELECT * from scores")
    fun getAllScores() : Flow<List<Score>>
}