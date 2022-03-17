package com.yusril.githubuserapp.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.yusril.githubuserapp.data.model.User

data class UserResponse(
    @SerializedName("items")
    var items: ArrayList<User>
)
