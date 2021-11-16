package com.example.classroom.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: Exam)

    @Query("SELECT * FROM examTable")
    fun getAll(): Flow<List<Exam>>
}