package com.example.studysmart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newstudysmart.data.local.ColorListConverter
import com.example.newstudysmart.data.local.SubjectDao
import com.example.newstudysmart.data.local.TaskDao
import com.example.newstudysmart.presentation.Domain.Model.Session
import com.example.newstudysmart.presentation.Domain.Model.Subject
import com.example.newstudysmart.presentation.Domain.Model.Task

@Database(
 entities = [Subject::class, Session::class, Task::class],
 version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class AppDatabase: RoomDatabase() {

 abstract fun subjectDao(): SubjectDao

 abstract fun taskDao(): TaskDao

 abstract fun sessionDao(): SessionDao
}