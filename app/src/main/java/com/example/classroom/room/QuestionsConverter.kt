package com.example.classroom.room

import androidx.room.TypeConverter
import com.example.classroom.model.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionsConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<Question>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<Question> {
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() {}.type
        return gson.fromJson(value, type)
    }
}