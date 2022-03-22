package com.yusril.githubuserapp.data.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("login")
    var login: String?,

    @SerializedName("avatar_url")
    var avatar_url: String?,

    @SerializedName("company")
    var company: String?,

    @SerializedName("location")
    var location: String?,

    @SerializedName("followers_url")
    var followers_url: String?,

    @SerializedName("following_url")
    var following_url: String?,

    @SerializedName("public_repos")
    var repos: Int?,

    @SerializedName("followers")
    var followers: Int?,

    @SerializedName("following")
    var following: Int?,
)
