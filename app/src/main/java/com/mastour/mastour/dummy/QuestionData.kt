package com.mastour.mastour.dummy

data class Question(
    val id: String,
    val question: String
)

object QuestionData {
    val questions = listOf(
        Question(
            "1",
            "I am the life of party"
        ),
        Question(
            "2",
            "I feel comfortable around people"
        ),
        Question(
            "3",
            "I start conversations"
        ),
        Question(
            "4",
            "I talk to a lot of different people at parties"
        ),
        Question(
            "5",
            "I don't mind being the center of attention"
        )
    )
}
