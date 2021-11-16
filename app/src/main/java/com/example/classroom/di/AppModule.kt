package com.example.classroom.di

import android.app.Application
import androidx.room.Room
import com.example.classroom.room.ExamDao
import com.example.classroom.room.ExamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesExamDatabase(application: Application) =
        Room.databaseBuilder(application, ExamDatabase::class.java, "ExamDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesExamDao(db: ExamDatabase): ExamDao = db.getExamDao()
}