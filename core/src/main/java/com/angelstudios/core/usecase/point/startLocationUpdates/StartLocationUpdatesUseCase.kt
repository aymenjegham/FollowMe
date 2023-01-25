package com.angelstudios.core.usecase.point.startLocationUpdates

import com.angelstudios.core.domain.point.Point
import kotlinx.coroutines.flow.Flow

interface StartLocationUpdatesUseCase {

    suspend operator fun invoke(): Flow<Point?>
}