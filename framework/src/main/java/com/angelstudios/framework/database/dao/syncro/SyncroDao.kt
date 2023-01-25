package com.angelstudios.framework.database.dao.syncro

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.angelstudios.framework.database.dao.BaseDao
import com.angelstudios.framework.entity.BaseEntity
import com.angelstudios.framework.entity.syncro.SyncroDataEntity
import com.angelstudios.framework.entity.syncro.SyncroEntity
import com.google.gson.Gson


@Dao
abstract class SyncroDao<T> : BaseDao<T> where T : BaseEntity, T : SyncroEntity  {

    @Insert
    abstract fun insertIntoSyncBuffer(entry: List<SyncroDataEntity>)

    @Insert
    abstract fun insertIntoSyncBuffer(entry: SyncroDataEntity)




    @Transaction
    open fun setDataToSync(entries: List<T>, gson: Gson) {
        insertOrUpdateIfExist(entries)
        insertIntoSyncBuffer(
            entries.map {
                SyncroDataEntity(
                    reference = it.id,
                    data = gson.toJson(it),
                    type = it.dataType
                )
            }
        )
    }

    @Transaction
    open fun setDataToSync(entry: T, gson: Gson) {
        insertOrUpdateIfExist(entry)
        insertIntoSyncBuffer(
            SyncroDataEntity(
                reference = entry.id,
                data = gson.toJson(entry),
                type = entry.dataType
            )
        )
    }
}