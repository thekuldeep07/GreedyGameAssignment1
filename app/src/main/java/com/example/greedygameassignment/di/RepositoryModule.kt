package com.example.greedygameassignment.di

import androidx.lifecycle.ViewModel
import com.example.greedygameassignment.data.repository.TagRepositoryImpl
import com.example.greedygameassignment.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTagRepository(
        repo:TagRepositoryImpl
    ): TagRepository
}