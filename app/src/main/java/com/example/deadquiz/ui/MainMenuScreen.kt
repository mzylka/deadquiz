package com.example.deadquiz.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deadquiz.R
import com.example.deadquiz.ui.theme.DeadQuizTheme

@Composable
fun MainMenuScreen(
    windowSize: WindowWidthSizeClass,
    onStartClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val logoHeight = when (windowSize) {
        WindowWidthSizeClass.Compact -> 128.dp
        else -> 224.dp
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.height(logoHeight)){
            Image(
                painter = painterResource(id = R.drawable.dead_quiz_logo),
                contentDescription = "Dead Quiz Logo"
            )
        }
        Button(
            onClick = {
                onStartClick()
            }
        ) {
            Text(text = "Start Quiz")
        }
        Button(
            onClick = {
                onStatisticsClick()
            }
        ) {
            Text(text = "Statistics")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMenuScreenPreview() {
    DeadQuizTheme {
        MainMenuScreen(
            windowSize = WindowWidthSizeClass.Compact,
            onStartClick = {},
            onStatisticsClick = {},
        )
    }
}