package com.yusril.githubuserapp.data.source.remote

import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.model.UserDetail
import com.yusril.githubuserapp.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Header("Authorization") token: String,
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<User>>
}