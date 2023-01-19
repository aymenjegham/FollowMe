package com.angelstudios.framework.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
abstract class BaseEntity(

    @PrimaryKey
    @ColumnInfo(name = "ID")
    @SerializedName("ID")
    @Expose
    val id: String


)