package com.mastour.mastour.data.local


data class Question(
    val question: String
)
object QuestionsData {
    val questions = listOf(
        Question("I am the life of the party"),
        Question("I feel comfortable around people"),
        Question("I start conversations"),
        Question("I talk to a lot of different people at parties"),
        Question("I don't mind being the center of attention"),
        Question("I get stressed out easily"),
        Question("I worry about things"),
        Question("I get upset easily"),
        Question("I change my mood a lot"),
        Question("I often feel blue"),
        Question("I sympathize with others' feelings"),
        Question("I have a soft heart"),
        Question("I take time out for others"),
        Question("I feel others' emotions"),
        Question("I make people feel at ease"),
        Question("I am always prepared"),
        Question("I get chores done right away"),
        Question("I like order"),
        Question("I follow a schedule"),
        Question("I am exacting in my work"),
        Question("I have a rich vocabulary"),
        Question("I have excellent ideas"),
        Question("I use difficult words"),
        Question("I spend time reflecting on things"),
        Question("I am full of ideas")
    )
}