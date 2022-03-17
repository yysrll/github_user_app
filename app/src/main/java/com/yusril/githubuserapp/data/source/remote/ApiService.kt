package com.yusril.githubuserapp.data.source.remote

import com.yusril.githubuserapp.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Header("Authorization") token: String,
        @Query("q") username: String
    ): Call<UserResponse>
}