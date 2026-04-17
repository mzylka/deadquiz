package com.example.deadquiz.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
    val maxScore: Int,
    val date: Long,
)