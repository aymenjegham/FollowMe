package com.angelstudios.framework.global.helper



import com.angelstudios.core.domain.point.Point
import com.angelstudios.core.domain.user.User
import com.angelstudios.core.global.helper.ObjectMapper
import com.angelstudios.framework.entity.point.PointEntity
import com.angelstudios.framework.entity.user.UserEntity
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class AppObjectMapper @Inject constructor(gson: Gson) : ObjectMapper(gson) {


    override val supportedMapping: Map<KClass<out Any>, KClass<out Any>>
        get() = mapOf(
            UserEntity::class to User::class,
            User::class to UserEntity::class,
            PointEntity::class to Point::class,
            Point::class to PointEntity::class,
        )
}