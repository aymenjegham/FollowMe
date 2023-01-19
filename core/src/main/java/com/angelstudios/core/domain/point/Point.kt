package com.angelstudios.core.domain.point

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class Point(

    @Expose
    @SerializedName("id")
    val id: String,


    @Expose
    @SerializedName("TAKEN_AT")
    val gpsTakenAt: Date?,

    @Expose
    @SerializedName("PHONE_TAKEN_AT")
    val phoneTakenAt: Date?,

    @Expose
    @SerializedName("IS_FAKE")
    val isFake: Boolean?,

    @Expose
    @SerializedName("LONGITUDE")
    val longitude: Double?,

    @Expose
    @SerializedName("LATITUDE")
    val latitude: Double?,

    @Expose
    @SerializedName("MOCK_PROVIDER")
    val mockProvider: Int?,

    @Expose
    @SerializedName("ACCURACY")
    val accuracy: Int?,

    @Expose
    @SerializedName("TRACE_ID")
    val traceId: String?,

    @Expose
    @SerializedName("SPEED")
    val speed: Double?


    ) : Serializable