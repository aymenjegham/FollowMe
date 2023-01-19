package com.angelstudios.framework.data.datasource.point

import android.location.Location
import android.os.SystemClock
import com.angelstudios.core.data.dataSource.point.LocationDataSource
import com.angelstudios.core.domain.point.Point
import com.angelstudios.framework.global.helper.LocationManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(private val locationManager: LocationManager) :
    LocationDataSource {


    @FlowPreview
    override fun startLocationUpdates(
        locationUpdateInterval: Long,
        locationUpdateFastInterval: Long
    ): Flow<Point?> {

        return locationManager.startLocationUpdate(locationUpdateInterval, locationUpdateFastInterval)
            .map {
                it?.let(mapToPoint) }
    }


    override suspend fun getUserCurrentLocation(): Point? =
        locationManager.getCurrentLocation()?.let(mapToPoint)

    override fun stopLocationUpdates() = locationManager.stopLocationUpdates()


    private val mapToPoint = fun(location: Location) = location.run {
        Point(
            "",
            Date(
                System.currentTimeMillis()
                        - SystemClock.elapsedRealtime()
                        + (elapsedRealtimeNanos / 1000000)
            ),
            Date(),
            location.isFromMockProvider,
            longitude,
            latitude,
            null,
            accuracy.toInt(),
            "",
            speed.times(3.6)

        )
    }

}