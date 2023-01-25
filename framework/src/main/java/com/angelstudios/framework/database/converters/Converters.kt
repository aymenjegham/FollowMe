package com.angelstudios.framework.database.converters

import androidx.room.TypeConverter
import com.angelstudios.framework.entity.syncro.SyncroDataType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    @TypeConverter
    fun timeStampToDate(value: Long?): Date? = value?.let {
        Date((value) * 1000)
    }

    @TypeConverter
    fun dateToTimeStamp(value: Date?): Long? = value?.let { it.time / 1000 }

    @TypeConverter
    fun stringToList(jsonString: String): List<String> =
        object : TypeToken<List<String>>() {}.type.let { Gson().fromJson(jsonString, it) }

    @TypeConverter
    fun listToString(list: List<String>): String = Gson().toJson(list)


     @TypeConverter
    fun stringToSyncroDataType(type: String) = SyncroDataType.fromRawValue(type)

    @TypeConverter
    fun syncroDataTypeToString(type: SyncroDataType) = type.toString()



}