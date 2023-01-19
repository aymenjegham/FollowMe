package com.angelstudios.core.domain.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(


    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("USERNAME")
    val username: String,

    @Expose
    @SerializedName("FIRST_NAME")
    val firstName: String?,

    @Expose
    @SerializedName("LAST_NAME")
    val lastName: String?,

    @Expose
    @SerializedName("EMAIL")
    val email: String?,

    @Expose
    @SerializedName("AVATAR")
    val avatar: String?,




    ) : Serializable{

    val userFullName: String
        get() = "$firstName $lastName"
    }