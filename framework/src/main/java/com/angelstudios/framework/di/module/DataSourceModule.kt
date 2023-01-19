package com.angelstudios.framework.di.module


import com.angelstudios.core.data.dataSource.point.LocationDataSource
import com.angelstudios.core.data.dataSource.point.PointDatabase
import com.angelstudios.core.data.dataSource.user.UserDatasource
import com.angelstudios.framework.data.datasource.point.LocationDataSourceImpl
import com.angelstudios.framework.data.datasource.point.PointDatabaseImpl
import com.angelstudios.framework.data.datasource.user.UserDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindsUserDatasource(userDatasource: UserDatasourceImpl) : UserDatasource

    @Singleton
    @Binds
    abstract fun bindLocationDataSource(dataSource: LocationDataSourceImpl): LocationDataSource

    @Binds
    abstract fun bindPointDatabase(dataSource: PointDatabaseImpl): PointDatabase
}
