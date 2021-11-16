package com.example.classroom.room

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun toCalendar(l: Long): Calendar {
        val c: Calendar = Calendar.getInstance()
        c.timeInMillis = l
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar): Long {
        return c.time.time
    }
}