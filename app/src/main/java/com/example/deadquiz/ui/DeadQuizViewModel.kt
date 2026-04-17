package com.example.deadquiz.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deadquiz.DeadlockApi
import com.example.deadquiz.data.DeadlockItem
import com.example.deadquiz.data.db.Item
import com.example.deadquiz.data.db.ItemsRepository
import com.example.deadquiz.data.Question
import com.example.deadquiz.data.db.Score
import com.example.deadquiz.data.db.ScoreDAO
import com.example.deadquiz.data.db.ScoreRepository
import com.example.deadquiz.data.toItem
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizState(
    val questions: List<Question> = listOf<Question>(),
    val currentQuestion: Int = 0,
    val selectedAnswer: String? = null,
    val score: Int = 0,
    val canGoNext: Boolean = false,
    val isGameOver: Boolean = false
)

sealed interface InitialState {
    object Success : InitialState
    object Error : InitialState
    object Loading : InitialState
}

class DeadQuizViewModel(
    private val itemsRepository: ItemsRepository,
    private val scoreRepository: ScoreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(QuizState())
    private var _initState: InitialState by mutableStateOf(InitialState.Loading)
    val uiState: StateFlow<QuizState> = _uiState.asStateFlow()
    val initState: InitialState
        get() = _initState

    val scores: StateFlow<List<Score>> = scoreRepository.getScores()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = listOf()
        )


    init {
        getAndSaveItems()
    }

    fun isGameOver(): Boolean {
        return _uiState.value.isGameOver
    }

    fun finishGame() {
        _uiState.update { currentState ->
            currentState.copy(
                isGameOver = true
            )
        }
    }

    fun getCurrentQuestion(): Question {
        return _uiState.value.questions[_uiState.value.currentQuestion]
    }

    fun startNewGame() {
        generateQuestions()
        _uiState.update { currentState ->
            currentState.copy(
                currentQuestion = 0,
                selectedAnswer = null,
                score = 0,
                canGoNext = false,
                isGameOver = false
            )
        }
    }

    private fun scoreUp() {
        _uiState.update { currentState ->
            currentState.copy(
                score = currentState.score + 1
            )
        }
    }

    fun checkAnswer(answer: String) {
        val isCorrect = answer == getCurrentQuestion().correctAnswer
        _uiState.update { currentState ->
            currentState.copy(
                selectedAnswer = answer
            )
        }

        if (isTheLastQuestion()) {
            finishGame()
        }
        if (isCorrect) {
            scoreUp()
        }
        _uiState.update { currentState ->
            currentState.copy(
                canGoNext = true
            )
        }
    }

    fun goNextQuestion() {
        if (isTheLastQuestion()) {
            _uiState.update { currentState ->
                currentState.copy(
                    isGameOver = true
                )
            }
        }
        else {
            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestion = currentState.currentQuestion + 1,
                    selectedAnswer = null,
                    canGoNext = false
                )
            }
        }
    }

    private fun isTheLastQuestion(): Boolean {
        return _uiState.value.currentQuestion == _uiState.value.questions.size - 1
    }

    private suspend fun saveItemsToDb(items: List<Item>){
        itemsRepository.insertItems(items)
    }

    private suspend fun getFromApi(): List<DeadlockItem> {
        val deadlockItems: List<DeadlockItem> = DeadlockApi.retrofitService.getItems()
        val shopItems = deadlockItems.filter { it.shopable && it.description?.desc != null }
        val ss = shopItems.map { item ->
            val cleanText = HtmlCompat
                .fromHtml(item.description?.desc!!, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
            DeadlockItem(item.name, item.cost, item.shopable, item.description.copy(desc = cleanText))
        }
        return ss
    }

    private suspend fun hasItemsInDb(): Boolean {
        return itemsRepository.hasItems()
    }

    private fun getAndSaveItems() {
        viewModelScope.launch {
            _initState = try {
                if (hasItemsInDb()) {
                    InitialState.Success
                }
                else {
                    val deadlockitems = async { getFromApi() }
                    val dItems = deadlockitems.await()
                    val items = dItems.map { item ->
                        item.toItem()
                    }
                    saveItemsToDb(items)
                    InitialState.Success
                }
            } catch (e: Exception) {
                InitialState.Error
            }
        }
    }

    private fun generateQuestions(count: Int = 10) {
        viewModelScope.launch {
            val items = itemsRepository.getAllItems()
            var usedNames = mutableListOf<Int>()
            val usedDescriptions = mutableListOf<Int>()
            val questionsList = mutableListOf<Question>()

            repeat(count) {
                var randomItem = items.random()

                while (usedDescriptions.contains(randomItem.id)) {
                    randomItem = items.random()
                }

                val itemId =  randomItem.id
                val desc = randomItem.description
                val name = randomItem.name

                usedNames.add(itemId)
                usedDescriptions.add(itemId)

                val answers = mutableListOf<String>()
                while (usedNames.size < 4) {
                    val randomItem = items.random()
                    if (!usedNames.contains(randomItem.id)) {
                        answers.add(randomItem.name)
                        usedNames.add(randomItem.id)
                    }
                }
                answers.add(name)
                answers.shuffle()
                usedNames = mutableListOf<Int>()
                questionsList.add(Question(desc, answers, name))
            }
            _uiState.update { currentState ->
                currentState.copy(
                    questions = questionsList
                )
            }
        }
    }

    fun saveScore() {
        viewModelScope.launch {
            val score = _uiState.value.score
            val maxScore = _uiState.value.questions.size
            val date: Long = System.currentTimeMillis()
            scoreRepository.insertScore(
                Score(
                    score = score,
                    maxScore = maxScore,
                    date = date
                )
            )
        }
    }
}
