package com.aymen.framework.dataSource.database


import com.angelstudios.framework.dataSource.database.UserDatabase
import com.angelstudios.framework.database.dao.user.UserDao
import javax.inject.Inject

class UserDatabaseImpl @Inject constructor(
    private val userDao: UserDao,
) : UserDatabase {



}