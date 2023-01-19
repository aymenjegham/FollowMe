package com.angelstudios.framework.global.helper

import javax.inject.Inject

class KalmanFilter @Inject constructor() {

    private var minAccuracy = 1f

    private var timeStampInMilli = 0L

    private var latitude = 0.0

    private var longitude = 0.0

    private var variance = -1f

    fun process(
        currentLatitude: Double,
        currentLongitude: Double,
        accuracy: Float,
        elapsedTimeInMillis: Long,
        QMetresPerSecond: Float
    ): Pair<Double, Double> {
        var currentAccuracy = accuracy

        if (currentAccuracy < minAccuracy)
            currentAccuracy = minAccuracy

        if (variance < 0) {
            timeStampInMilli = elapsedTimeInMillis
            latitude = currentLatitude
            longitude = currentLongitude
            variance = currentAccuracy * currentAccuracy
        } else {
            val intervalInMill = elapsedTimeInMillis - timeStampInMilli

            if (intervalInMill > 0) {
                variance += intervalInMill * QMetresPerSecond * QMetresPerSecond / 1000
                timeStampInMilli = elapsedTimeInMillis
            }

            val k = variance / (variance + currentAccuracy * currentAccuracy)

            latitude += k * (currentLatitude - latitude)
            longitude += k * (currentLongitude - longitude)
            variance *= (1 - k)
        }

        return latitude to longitude
    }
}