package com.example.newstudysmart.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newstudysmart.presentation.Domain.Model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId : Int)

    @Query("DELETE FROM task WHERE taskSubjectId = :subjectId")
    suspend fun deleteTasksBySubjectId(subjectId: Int)

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query ("SELECT * FROM Task WHERE taskSubjectId = :subjectId")
    fun getTasksForSubject(subjectId : Int): Flow<List<Task>>

    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

}