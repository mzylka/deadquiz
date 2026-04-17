package com.example.deadquiz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deadquiz.data.DataExample
import com.example.deadquiz.data.Question
import com.example.deadquiz.ui.theme.DeadQuizTheme

@Composable
fun QuizScreen(
    windowSize: WindowWidthSizeClass,
    question: Question,
    answered: String?,
    onBackClick: () -> Unit,
    onAnswerClick: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    canGoNext: Boolean = false,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Question(
                question.question,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Answers(
                windowSize = windowSize,
                answers = question.answers,
                answered = answered,
                correctAns = question.correctAnswer,
                onAnswerClick = onAnswerClick
            )
        }
        BottomBar(
            onBackClick = onBackClick,
            onNextClick = onNextClick,
            canGoNext = canGoNext,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}


@Composable
fun Question(question: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = question,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Answers(
    windowSize: WindowWidthSizeClass,
    answers: List<String>,
    answered: String?,
    correctAns: String,
    onAnswerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (windowSize == WindowWidthSizeClass.Compact) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            answers.forEach { answer ->
                if (answered != null) {
                    AnswerButton(
                        text = answer,
                        answered = answered,
                        correct = correctAns,
                        onClick = onAnswerClick,
                        modifier = Modifier.widthIn(min = 256.dp)
                    )
                } else {
                    AnswerButton(
                        text = answer,
                        answered = null,
                        correct = correctAns,
                        onClick = onAnswerClick,
                        modifier = Modifier.widthIn(min = 256.dp)
                    )
                }
            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
        ) {
            answers.forEach { answer ->
                if (answered != null) {
                    AnswerButton(
                        text = answer,
                        answered = answered,
                        correct = correctAns,
                        onClick = onAnswerClick,
                        modifier = Modifier.widthIn(min = 172.dp)
                    )
                } else {
                    AnswerButton(
                        text = answer,
                        answered = null,
                        correct = correctAns,
                        onClick = onAnswerClick,
                        modifier = Modifier.widthIn(min = 172.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerButton(
    text: String,
    answered: String?,
    correct: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isCorrect = correct == answered
    val color = when {
        answered == null -> MaterialTheme.colorScheme.primary
        isCorrect && text == answered -> Color.Green
        text == answered -> Color.Red
        text == correct -> Color.Yellow
        else -> MaterialTheme.colorScheme.primary
    }

    Button(
        onClick = {
            if (answered == null){
                onClick(text)
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomBar(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    canGoNext: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        ElevatedButton(
            onClick = onBackClick,
        ) {
            Text(text = "Back To Menu")
        }
        ElevatedButton(
            onClick = onNextClick,
            enabled = canGoNext
        ) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreenPreview() {
    DeadQuizTheme {
        QuizScreen(
            windowSize = WindowWidthSizeClass.Compact,
            question = DataExample.test[0],
            answered = null,
            onBackClick = {},
            onAnswerClick = {},
            onNextClick = {}
        )
    }
}
