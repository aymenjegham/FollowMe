package com.angelstudios.framework.di.module

import com.angelstudios.framework.database.Database
import com.angelstudios.framework.database.dao.point.PointDao
import com.angelstudios.framework.database.dao.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DaoModule {


    @Provides
    @Singleton
    fun providePointDao(database: Database): PointDao = database.pointDao()

    @Provides
    @Singleton
    fun provideUserDao(database: Database): UserDao = database.userDao()
}