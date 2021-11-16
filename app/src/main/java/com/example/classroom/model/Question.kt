package com.example.classroom.model

data class Question(
    val id: Int,
    val question: String,
    val isEditing: Boolean,
    val options: List<String>
)

val questions = listOf(
    Question(0,"How are you?", false, listOf("Fine", "Good", "Feeling Bad", "Happy")),
    Question(1,"What is your age?", false, listOf("16", "17", "20", "18"))
)
