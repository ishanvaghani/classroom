package com.example.classroom.ui.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.example.classroom.repository.MainRepository
import com.example.classroom.room.Exam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    init {
        getAll()
    }

    val exams = MutableLiveData<List<Exam>>()

    private fun getAll() = viewModelScope.launch {
        mainRepository.getAll()
            .catch {
                it.message?.let { it1 ->
                    Log.d("main", it1)
                }
            }
            .collect {
                exams.value = it
            }
    }
}