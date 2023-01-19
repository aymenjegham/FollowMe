package com.angelstudios.framework.entity.syncro

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "SYNCRO_BUFFER")
data class SyncroDataEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    @Expose
    @SerializedName("ID")
    val id: Int = 0,

    @ColumnInfo(name = "REFERENCE")
    @Expose
    @SerializedName("REFERENCE")
    val reference: String,

    @ColumnInfo(name = "DATA")
    @Expose
    @SerializedName("DATA")
    val data: String,

    @ColumnInfo(name = "DATA_TYPE")
    @Expose
    @SerializedName("DATA_TYPE")
    val type: SyncroDataType
)