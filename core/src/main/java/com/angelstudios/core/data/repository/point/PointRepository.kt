package com.angelstudios.core.data.repository.point


import com.angelstudios.core.domain.point.Point
import kotlinx.coroutines.flow.Flow

interface PointRepository {

    fun startLocationUpdate(
        locationUpdateInterval: Long,
        locationUpdateFastInterval: Long
    ): Flow<Point?>

    suspend fun getCurrentUserLocation(): Point?

    suspend fun addPoints(points: List<Point>)

    fun stopLocationUpdates()

    fun getPoints(traceId: String): Flow<List<Point>>

}