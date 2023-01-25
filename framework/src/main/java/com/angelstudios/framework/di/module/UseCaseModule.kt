package com.angelstudios.framework.di.module


import com.angelstudios.core.usecase.point.startLocationUpdates.StartLocationUpdatesUseCase
import com.angelstudios.core.usecase.point.startLocationUpdates.StartLocationUpdatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindStartLocationUpdatesUseCase(useCase: StartLocationUpdatesUseCaseImpl): StartLocationUpdatesUseCase



}