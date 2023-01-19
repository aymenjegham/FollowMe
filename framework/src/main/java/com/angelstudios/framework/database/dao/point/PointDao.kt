package com.angelstudios.framework.database.dao.point

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.angelstudios.framework.database.dao.syncro.SyncroDao
import com.angelstudios.framework.entity.point.PointEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class PointDao : SyncroDao<PointEntity>() {

    @Query("select * from Point  where TRACE_ID = :traceId ORDER BY PHONE_TAKEN_AT")
    abstract fun getPoints(traceId: String): Flow<List<PointEntity>>

}