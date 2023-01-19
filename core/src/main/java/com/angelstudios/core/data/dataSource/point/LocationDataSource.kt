package com.angelstudios.core.data.dataSource.point

import com.angelstudios.core.domain.point.Point
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {


    fun startLocationUpdates(
        locationUpdateInterval: Long,
        locationUpdateFastInterval: Long
    ): Flow<Point?>


    suspend fun getUserCurrentLocation(): Point?

    fun stopLocationUpdates()

}