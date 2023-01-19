package com.angelstudios.core.data.repository.user


import com.angelstudios.core.data.dataSource.user.UserDatasource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val datasource: UserDatasource,
) : UserRepository {

}