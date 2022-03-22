package com.yusril.githubuserapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    var login: String,

    @SerializedName("avatar_url")
    var avatar_url: String,
) : Parcelable
