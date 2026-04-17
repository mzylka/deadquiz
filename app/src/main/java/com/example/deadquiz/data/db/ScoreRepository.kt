package com.example.deadquiz.data.db

import kotlinx.coroutines.flow.Flow

class ScoreRepository(private val scoreDAO: ScoreDAO) {
    fun getScores(): Flow<List<Score>> {
        return scoreDAO.getAllScores()
    }

    suspend fun insertScore(score: Score) {
        scoreDAO.insert(score)
    }
}