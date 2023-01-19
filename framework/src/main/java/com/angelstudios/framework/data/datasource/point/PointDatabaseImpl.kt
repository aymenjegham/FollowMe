package com.angelstudios.framework.data.datasource.point


import com.angelstudios.core.data.dataSource.point.PointDatabase
import com.angelstudios.core.domain.point.Point
import com.angelstudios.framework.database.dao.point.PointDao
import com.angelstudios.framework.entity.point.PointEntity
import com.angelstudios.framework.global.helper.AppObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PointDatabaseImpl @Inject constructor(
    private val pointDao: PointDao,
    private val appObjectMapper: AppObjectMapper,
    private val gson: Gson
) : PointDatabase {

    override suspend fun addPoints(points: List<Point>) =
        points.map { appObjectMapper.map(it, PointEntity::class) }
            .let { pointDao.setDataToSync(it, gson) }

    override fun getPoints(traceId: String): Flow<List<Point>> =
        pointDao.getPoints(traceId).map {
            it.map {
                appObjectMapper.map(it, Point::class)
            }
    }


}