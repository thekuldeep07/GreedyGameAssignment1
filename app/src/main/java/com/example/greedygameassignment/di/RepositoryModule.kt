package com.example.greedygameassignment.di

import com.example.greedygameassignment.domain.repository.RepositoryImpl
import com.example.greedygameassignment.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTagRepository(
        repo: RepositoryImpl
    ): Repository
}