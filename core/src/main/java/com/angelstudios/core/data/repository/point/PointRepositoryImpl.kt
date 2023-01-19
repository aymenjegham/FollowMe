package com.angelstudios.core.data.repository.point


import com.angelstudios.core.data.dataSource.point.LocationDataSource
import com.angelstudios.core.data.dataSource.point.PointDatabase
import com.angelstudios.core.data.repository.point.PointRepository
import com.angelstudios.core.domain.point.Point
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val pointDatabase: PointDatabase,
) : PointRepository {


    override fun startLocationUpdate(
        locationUpdateInterval: Long,
        locationUpdateFastInterval: Long
    ) = locationDataSource.startLocationUpdates(locationUpdateInterval, locationUpdateFastInterval)

    override suspend fun getCurrentUserLocation(): Point? =
        locationDataSource.getUserCurrentLocation()

    override suspend fun addPoints(points: List<Point>) = pointDatabase.addPoints(points)

    override fun stopLocationUpdates()  = locationDataSource.stopLocationUpdates()

    override fun getPoints(traceId: String): Flow<List<Point>> = pointDatabase.getPoints(traceId)

}