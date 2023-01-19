package com.angelstudios.framework.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    fun insertOrUpdateIfExist(obj: List<T>)

    @Insert(onConflict = REPLACE)
    fun insertOrUpdateIfExist(obj: T)
}