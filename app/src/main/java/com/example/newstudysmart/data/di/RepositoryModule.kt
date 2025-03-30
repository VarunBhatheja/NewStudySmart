package com.example.newstudysmart.data.di


import com.example.newstudysmart.data.repository.SubjectRepositoryImpl
import com.example.newstudysmart.data.repository.TaskRepository
import com.example.newstudysmart.data.repository.TaskRepositoryImpl
import com.example.newstudysmart.data.repository.SessionRepositoryImpl
import com.example.newstudysmart.presentation.Domain.repository.SessionRepository
import com.example.studysmart.domain.repository.SubjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent:: class)
abstract class RepositoryModule {


    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl : TaskRepositoryImpl
    ) : TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl : SessionRepositoryImpl
    ) : SessionRepository
}