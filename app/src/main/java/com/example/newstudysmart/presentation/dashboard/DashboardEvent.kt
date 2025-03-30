package com.example.studysmart.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.example.newstudysmart.presentation.Domain.Model.Session
import com.example.newstudysmart.presentation.Domain.Model.Task

sealed class DashboardEvent {
    data object SaveSubject : DashboardEvent()
    data object DeleteSession : DashboardEvent()
//     if some value will from user then will use data class otherwise data objects
    data class OnDeleteSessionButtonClick(val session: Session): DashboardEvent()
    data class OnTaskIsCompleteChange(val task: Task): DashboardEvent()
    data class OnSubjectCardColorChange(val colors: List<Color>): DashboardEvent()
    data class OnSubjectNameChange(val name: String): DashboardEvent()
    data class OnGoalStudyHoursChange(val hours: String): DashboardEvent()
}
