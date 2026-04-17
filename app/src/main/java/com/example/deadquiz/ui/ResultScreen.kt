package com.example.deadquiz.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ResultScreen(
    onBackPressed: () -> Unit,
    score: Int,
    maxScore: Int,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Your score: $score/$maxScore")
        Button(onClick = { onBackPressed() }) {
            Text(text = "Back to menu")
        }
    }
}