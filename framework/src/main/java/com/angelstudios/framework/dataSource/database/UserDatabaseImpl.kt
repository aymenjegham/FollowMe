package com.aymen.framework.dataSource.database


import androidx.paging.PagingSource
import com.aymen.framework.database.UserDao
import com.aymen.framework.entity.UserEntity
import javax.inject.Inject

class UserDatabaseImpl @Inject constructor(
    private val userDao: UserDao,
) : UserDatabase {
    override fun getAll(): PagingSource<Int, UserEntity> = userDao.getAll()

    override suspend fun insertAll(users: List<UserEntity>) = userDao.insertAll(users)

    override suspend fun deleteAll() = userDao.deleteAll()


}