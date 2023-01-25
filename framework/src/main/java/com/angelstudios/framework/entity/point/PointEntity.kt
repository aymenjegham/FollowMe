package com.angelstudios.framework.entity.point

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.angelstudios.framework.entity.BaseEntity
import com.angelstudios.framework.entity.syncro.SyncroDataType
import com.angelstudios.framework.entity.syncro.SyncroEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "Point", indices = [Index(value = ["id"], unique = true)])
class PointEntity(

    id: String,

    @Expose
    @ColumnInfo(name = "TAKEN_AT")
    @SerializedName("TAKEN_AT")
    val gpsTakenAt: Date?,

    @Expose
    @ColumnInfo(name = "PHONE_TAKEN_AT")
    @SerializedName("PHONE_TAKEN_AT")
    val phoneTakenAt: Date?,

    @Expose
    @ColumnInfo(name = "IS_FAKE")
    @SerializedName("IS_FAKE")
    val isFake: Boolean?,

    @Expose
    @ColumnInfo(name = "LONGITUDE")
    @SerializedName("LONGITUDE")
    val longitude: Double?,

    @Expose
    @ColumnInfo(name = "LATITUDE")
    @SerializedName("LATITUDE")
    val latitude: Double?,

    @Expose
    @ColumnInfo(name = "MOCK_PROVIDER")
    @SerializedName("MOCK_PROVIDER")
    val mockProvider: Int?,

    @Expose
    @ColumnInfo(name = "ACCURACY")
    @SerializedName("ACCURACY")
    val accuracy: Int?,

    @Expose
    @ColumnInfo(name = "TRACE_ID")
    @SerializedName("TRACE_ID")
    val traceId: String?,

    @Expose
    @ColumnInfo(name = "SPEED")
    @SerializedName("SPEED")
    val speed: Double?,
) : BaseEntity(id), SyncroEntity {

    override val dataType: SyncroDataType
        get() = SyncroDataType.POINT
}