package com.example.classroom.ui.exams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classroom.model.Question
import com.example.classroom.repository.MainRepository
import com.example.classroom.room.Exam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val classRoom = MutableLiveData<Int>()
    val syllabus = MutableLiveData<String>()
    val dateTime = MutableLiveData<Calendar>()
    val duration = MutableLiveData<String>()
    val timeframe = MutableLiveData<String>()
    val totalMark = MutableLiveData<Int>()
    val category = MutableLiveData<String>()

    fun insert(exam: Exam) = viewModelScope.launch {
        mainRepository.insert(exam)
    }
}