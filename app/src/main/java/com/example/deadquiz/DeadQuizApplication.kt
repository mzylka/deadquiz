package com.example.deadquiz

import android.app.Application

class DeadQuizApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}