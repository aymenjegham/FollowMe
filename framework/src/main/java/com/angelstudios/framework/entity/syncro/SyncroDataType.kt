package com.angelstudios.framework.entity.syncro

enum class SyncroDataType {

    USER,
    POINT;

    companion object {
        private val map = values().associateBy(SyncroDataType::toString)
        fun fromRawValue(type: String) = map[type]
    }

}