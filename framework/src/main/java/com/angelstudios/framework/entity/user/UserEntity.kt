package com.angelstudios.framework.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.angelstudios.framework.entity.BaseEntity
import com.angelstudios.framework.entity.syncro.SyncroDataType
import com.angelstudios.framework.entity.syncro.SyncroEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User", indices = [Index(value = ["id"], unique = true)])
class UserEntity(

    id: String,

    @ColumnInfo(name = "USERNAME")
    @Expose
    @SerializedName("USERNAME")
    val username: String,

    @ColumnInfo(name = "FIRST_NAME")
    @Expose
    @SerializedName("FIRST_NAME")
    val firstName: String?,

    @ColumnInfo(name = "LAST_NAME")
    @Expose
    @SerializedName("LAST_NAME")
    val lastName: String?,

    @ColumnInfo(name = "EMAIL")
    @Expose
    @SerializedName("EMAIL")
    val email: String?,

    @ColumnInfo(name = "AVATAR")
    @Expose
    @SerializedName("AVATAR")
    val avatar: String?,

    ) : BaseEntity(id), SyncroEntity {

    override val dataType: SyncroDataType
        get() = SyncroDataType.POINT
}
