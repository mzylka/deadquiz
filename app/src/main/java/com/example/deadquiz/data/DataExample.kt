package com.example.deadquiz.data

object DataExample {
    val test = listOf<Question>(
        Question(
            "Question 1 - Option 2",
            listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            "Option 2"
        ),
        Question(
            "Question 2 - Option 1",
            listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            "Option 1"
        ),
        Question(
            "Question 3 - Option 3",
            listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            "Option 3"
        ),
    )
}