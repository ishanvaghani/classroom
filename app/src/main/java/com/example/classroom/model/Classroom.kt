package com.example.classroom.model

data class Classroom(
    val id: Int,
    val name: String,
    val totalClass: Int,
    val attendedClasses: Int,
    val missedClasses: Int,
    val leftClasses: Int
)

val userData = listOf(
    Classroom(0, "Data Structures & Algorithms", 70, 34, 6, 30),
    Classroom(1,"Maths", 70, 20, 15, 35),
    Classroom(2,"Java", 70, 31, 9, 30),
)