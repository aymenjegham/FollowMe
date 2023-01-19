package com.angelstudios.core.data.dataSource.point


import com.angelstudios.core.domain.point.Point
import kotlinx.coroutines.flow.Flow

interface PointDatabase {

    suspend fun addPoints(points: List<Point>)

    fun getPoints(
        id :String
    ): Flow<List<Point>>


}