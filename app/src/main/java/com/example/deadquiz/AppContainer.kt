package com.example.deadquiz

import android.content.Context
import com.example.deadquiz.data.db.DeadquizDatabase
import com.example.deadquiz.data.db.ItemsRepository
import com.example.deadquiz.data.db.ItemsRepositoryOffline
import com.example.deadquiz.data.db.ScoreRepository

interface AppContainer {
    val itemsRepository: ItemsRepository
    val scoreRepository: ScoreRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        ItemsRepositoryOffline(DeadquizDatabase.getDatabase(context).itemDAO())
    }

    override val scoreRepository: ScoreRepository by lazy {
        ScoreRepository(DeadquizDatabase.getDatabase(context).scoreDAO())
    }
}