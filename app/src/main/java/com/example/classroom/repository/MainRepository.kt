package com.example.classroom.repository

import com.example.classroom.room.Exam
import com.example.classroom.room.ExamDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: ExamDao) {

    suspend fun insert(exam: Exam) = withContext(Dispatchers.IO) {
        dao.insert(exam)
    }

    fun getAll(): Flow<List<Exam>> = dao.getAll()
}