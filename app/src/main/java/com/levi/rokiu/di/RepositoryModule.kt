package com.levi.rokiu.di

import com.levi.rokiu.data.repository.RokuRepositoryImpl
import com.levi.rokiu.domain.repository.RokuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRokuRepository(
        impl: RokuRepositoryImpl
    ): RokuRepository
}