package com.example.deadquiz

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deadquiz.ui.AppViewModelProvider
import com.example.deadquiz.ui.DeadQuizViewModel
import com.example.deadquiz.ui.InitialState
import com.example.deadquiz.ui.MainMenuScreen
import com.example.deadquiz.ui.QuizScreen
import com.example.deadquiz.ui.QuizState
import com.example.deadquiz.ui.ResultScreen
import com.example.deadquiz.ui.StatisticsScreen
import kotlinx.serialization.Serializable

@Serializable
object MainMenu
@Serializable
object Quiz
@Serializable
object Result
@Serializable
object Statistics

@SuppressLint("ContextCastToActivity")
@Composable
fun DeadQuizApp(
    windowSize: WindowWidthSizeClass,
    viewModel: DeadQuizViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val initState = viewModel.initState

    when (initState) {
        is InitialState.Success -> {
            Success(windowSize, navController, viewModel, state, modifier)
        }
        is InitialState.Error -> ErrorScreen(modifier)
        is InitialState.Loading -> LoadingScreen(modifier)
    }
}

@Composable
fun Success(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController,
    viewModel: DeadQuizViewModel,
    state: State<QuizState>,
    modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = MainMenu, modifier = modifier) {
        composable<MainMenu> {
            MainMenuScreen(
                windowSize = windowSize,
                onStartClick = {
                    viewModel.startNewGame()
                    navController.navigate(Quiz)
                },
                onStatisticsClick = { navController.navigate(Statistics) }
            )
        }

        composable<Quiz> {
            QuizScreen(
                windowSize = windowSize,
                question = state.value.questions[state.value.currentQuestion],
                answered = state.value.selectedAnswer,
                onBackClick = { navController.popBackStack(MainMenu, false) },
                onAnswerClick = { answer ->
                    viewModel.checkAnswer(answer)
                },
                onNextClick = {
                    if (viewModel.isGameOver()) {
                        viewModel.saveScore()
                        navController.navigate(Result)
                    }
                    viewModel.goNextQuestion()
                },
                canGoNext = state.value.canGoNext
            )
        }

        composable<Result> {
            ResultScreen(
                score = state.value.score,
                maxScore = state.value.questions.size,
                onBackPressed = {navController.popBackStack(MainMenu, false)}

            )
        }

        composable<Statistics> {
            StatisticsScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Error", fontWeight = FontWeight.Bold, color = Color.Red)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Loading", color = Color.White)
    }
}