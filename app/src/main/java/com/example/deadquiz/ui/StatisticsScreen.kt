package com.example.deadquiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deadquiz.data.db.Score
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StatisticsScreen(viewModel: DeadQuizViewModel, modifier: Modifier = Modifier) {
    val scores by viewModel.scores.collectAsState()
    val reversed = scores.reversed()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(reversed) {score ->
            StatisticsItem(score, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun StatisticsItem(score: Score, modifier: Modifier = Modifier) {
    val localDateTime = Instant.ofEpochMilli(score.date)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val formattedDateTime = localDateTime.format(formatter)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp)
    ) {
        val fontSize = MaterialTheme.typography.labelLarge
        Text(
            text = "${score.score}/${score.maxScore}",
            color = MaterialTheme.colorScheme.onPrimary,
            style = fontSize
        )
        Text(
            text = formattedDateTime,
            color = MaterialTheme.colorScheme.onPrimary,
            style = fontSize,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}