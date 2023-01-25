package com.angelstudios.framework.global.helper

import android.annotation.SuppressLint
import android.location.Location
import android.os.SystemClock
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.resume


class LocationManager @Inject constructor(private val locationProvider: FusedLocationProviderClient) :
    LocationCallback() {

    var scope = CoroutineScope(Dispatchers.IO)

    @ExperimentalCoroutinesApi
    private var locationChannel: ConflatedBroadcastChannel<Location> = ConflatedBroadcastChannel()

    @ExperimentalCoroutinesApi
    private var locationAvailabilityChannel: ConflatedBroadcastChannel<LocationAvailability> =
        ConflatedBroadcastChannel()

    private var currentSpeed = 0f

    private lateinit var kalmanFilter: KalmanFilter

    private var startRunTime = 0L

    private var consecutiveRejectCount = 0

    protected var locationUpdateInterval: Long = 0

    protected var locationUpdateFastInterval: Long = 0

    protected var locationUpdateIsRunning = false

    private var isFirstFix = true

    private val cachedPoints = mutableListOf<Location>()

    private lateinit var locationRequest: LocationRequest


    @FlowPreview
    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    open fun startLocationUpdate(
        locationUpdateInterval: Long,
        locationUpdateFastInterval: Long,
    ): Flow<Location?> {

        if (locationUpdateIsRunning) {
            stopLocationUpdates()
        }

        resetSettings()
        startRunTime = SystemClock.elapsedRealtimeNanos() / 1000000
        setLocationRequest(locationUpdateInterval, locationUpdateFastInterval)
        locationProvider.requestLocationUpdates(locationRequest, this, null)
        locationUpdateIsRunning = true

        return locationChannel.asFlow()
    }


    @ExperimentalCoroutinesApi
    open fun stopLocationUpdates() {
        locationProvider.removeLocationUpdates(this)
        locationChannel.cancel()
        locationChannel = ConflatedBroadcastChannel()
        scope.cancel()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @SuppressLint("MissingPermission")
    open suspend fun getCurrentLocation(): Location? =
        suspendCancellableCoroutine {
            val ctc = CancellationTokenSource()
            val location = Tasks.await(
                locationProvider.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    ctc.token
                )
            )
            it.invokeOnCancellation {
                ctc.cancel()
            }

            it.resume(location)
        }


    override fun onLocationResult(result: LocationResult) {
        result.lastLocation?.process()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onLocationAvailability(availability: LocationAvailability) {
        scope.launch {
            availability.let {
                locationAvailabilityChannel.send(availability)
            }

        }
    }

    private fun resetSettings() {
        currentSpeed = 0f
        startRunTime = 0L
        consecutiveRejectCount = 0
        locationUpdateInterval = 0
        locationUpdateFastInterval = 0
        kalmanFilter = KalmanFilter()
        scope = CoroutineScope(Dispatchers.IO)

    }


    private fun Location.process() {

        if ((age > 5000 || accuracy <= 0 || accuracy > 20) && !isFirstFix) {
            return
        }

        val elapsedTimeInMilli = (elapsedRealtimeNanos / 1000000) - startRunTime

        val qValue = if (currentSpeed == 0f) 3.0f else currentSpeed

        val (predictedLatitude, predictedLongitude) = kalmanFilter.process(
            latitude,
            longitude,
            accuracy,
            elapsedTimeInMilli,
            qValue
        )

        val predictedLocation = Location(this)
        predictedLocation.latitude = predictedLatitude
        predictedLocation.longitude = predictedLongitude

        val predictedDeltaInMeters = predictedLocation.distanceTo(this)

        if (predictedDeltaInMeters > 60) {
            consecutiveRejectCount++

            if (consecutiveRejectCount > 3) {
                consecutiveRejectCount = 0
                kalmanFilter = KalmanFilter()
            }
            return
        } else {
            consecutiveRejectCount = 0
        }

        currentSpeed = speed

        if (predictedLocation.accuracy > 10 && !isFirstFix) {
            cachedPoints.add(0, predictedLocation)
            if (cachedPoints.size == 5) {
                val selectedPoint = cachedPoints.sortedWith { location1, location2 ->
                    var diff = (location1.accuracy - location2.accuracy).toInt()
                    if (diff == 0) {
                        diff = (location1.age - location2.age).toInt()
                    }
                    diff

                }.first()

                pushFix(selectedPoint)

            }
        } else {
            isFirstFix = false
            pushFix(predictedLocation)
        }


    }

    private fun pushFix(predictedLocation: Location) {
        scope.launch {
            locationChannel?.send(predictedLocation)
            cachedPoints.clear()
        }
    }

    private val Location.age: Long
        get() = SystemClock.elapsedRealtime() - (elapsedRealtimeNanos / 1000000)

    private fun setLocationRequest(interval: Long, fastestInterval: Long) {

        locationUpdateInterval = interval
        locationUpdateFastInterval = fastestInterval


        locationRequest = LocationRequest
            .create()
            .setFastestInterval(locationUpdateFastInterval)
            .setInterval(locationUpdateInterval)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)


    }

    fun listenForLocationAvailability(): Flow<LocationAvailability> =
        locationAvailabilityChannel.asFlow()

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun listenForLocationUpdate(): Flow<Location> = locationChannel.asFlow()

}