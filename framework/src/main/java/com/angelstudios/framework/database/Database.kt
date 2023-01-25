package com.angelstudios.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.angelstudios.framework.database.converters.Converters
import com.angelstudios.framework.database.dao.point.PointDao
import com.angelstudios.framework.database.dao.user.UserDao
import com.angelstudios.framework.entity.point.PointEntity
import com.angelstudios.framework.entity.syncro.SyncroDataEntity
import com.angelstudios.framework.entity.user.UserEntity


const val DATABASE_NAME = "follow_me_db"
const val DATABASE_VERSION = 3

@Database(
    entities = [
        PointEntity::class,
        UserEntity::class,
        SyncroDataEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun pointDao(): PointDao

    abstract fun userDao(): UserDao
}