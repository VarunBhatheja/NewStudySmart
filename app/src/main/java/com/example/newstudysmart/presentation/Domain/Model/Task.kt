package com.example.newstudysmart.presentation.Domain.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    val title : String ,
    val description : String ,
    val dueDate : Long ,
    val priority : Int ,
    val relatedToSubject : String ,
    val isComplete : Boolean ,
    val taskSubjectId : Int ,
    @PrimaryKey(autoGenerate = true)
    val TaskId  : Int? = null
)