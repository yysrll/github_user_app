package com.yusril.githubuserapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    @SerializedName("login")
    var login: String,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    var avatar_url: String,
) : Parcelable
