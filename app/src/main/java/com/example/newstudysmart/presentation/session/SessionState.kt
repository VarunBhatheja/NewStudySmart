package com.example.studysmart.presentation.session

import com.example.newstudysmart.presentation.Domain.Model.Session
import com.example.newstudysmart.presentation.Domain.Model.Subject

data class SessionState(
    val subjects: List<Subject> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToSubject: String? = null,
    val subjectId: Int? = null,
    val session: Session? = null
)
