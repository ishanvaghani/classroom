package com.example.classroom.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.classroom.model.Question
import java.util.*

@Entity(tableName = "examTable")
data class Exam(
    val classroom: Int,
    val syllabus: String,
    val dateTime: Calendar,
    val duration: String,
    val timeframe: String,
    val totalMarks: Int,
    val category: String,
    val instructions: String,
    val sectionName: String,
    val sectionDescription: String?,
    val questions: List<Question>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
