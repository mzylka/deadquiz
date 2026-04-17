package com.example.deadquiz.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.deadquiz.DeadQuizApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            DeadQuizViewModel(
                deadquizApplication().container.itemsRepository,
                deadquizApplication().container.scoreRepository
            )
        }
    }
}

fun CreationExtras.deadquizApplication(): DeadQuizApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DeadQuizApplication)