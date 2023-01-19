package com.aymen.framework.dataSource.database


import androidx.paging.PagingSource
import com.aymen.framework.entity.UserEntity


interface UserDatabase {

    fun getAll(): PagingSource<Int, UserEntity>

    suspend fun insertAll(users: List<UserEntity>)

    suspend fun deleteAll()

}