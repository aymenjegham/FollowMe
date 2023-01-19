package com.angelstudios.core.helper

import com.google.gson.Gson
import kotlin.reflect.KClass

abstract class ObjectMapper(private val gson: Gson) {


    protected abstract val supportedMapping: Map<KClass<out Any>, KClass<out Any>>


    @Throws(IllegalArgumentException::class)
    fun <T : Any, R : Any> map(entry: T, mappedObjectClass: KClass<R>): R {
        return when {
            !supportedMapping.containsKey(entry::class) ->
                throw IllegalArgumentException("")
            supportedMapping[entry::class] != mappedObjectClass ->
                throw IllegalArgumentException("")
            else -> gson.run {
                val json = toJson(entry)
                fromJson(json, mappedObjectClass.java)
            }
        }

    }
}