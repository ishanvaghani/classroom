package com.example.classroom.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(CalendarConverter::class, QuestionsConverter::class)
@Database(entities = [Exam::class], version = 1, exportSchema = false)
abstract class ExamDatabase: RoomDatabase() {

    abstract fun getExamDao(): ExamDao
}