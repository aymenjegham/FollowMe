package com.angelstudios.core.usecase.point.startLocationUpdates


import com.angelstudios.core.data.repository.point.PointRepository
import com.angelstudios.core.data.repository.user.UserRepository
import com.angelstudios.core.domain.point.Point
import com.angelstudios.core.global.utils.uuid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class StartLocationUpdatesUseCaseImpl @Inject constructor(
    private val pointRepository: PointRepository,
    private val userRepository: UserRepository,
) :
    StartLocationUpdatesUseCase {


    private var locationUpdateInterval = 3000L
    private var locationUpdateFastInterval = 1000L

    override suspend fun invoke(): Flow<Point?> {


        return pointRepository.startLocationUpdate(
            locationUpdateInterval,
            locationUpdateFastInterval
        )
            .map {

                it?.copy(id = uuid(), traceId = Date().toString())

            }.let {
                it.onEach { point ->
                    point?.let { p ->
                        withContext(Dispatchers.IO) {
                            pointRepository.addPoints(listOf(p))
                        }
                    }
                }
            }
    }
}