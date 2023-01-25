package com.angelstudios.framework.di.module


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module
@InstallIn(ServiceComponent::class)
abstract class ServiceModule {

//    @ServiceScoped
//    @Binds
//    abstract fun contributeLocationService(): LocationService


}