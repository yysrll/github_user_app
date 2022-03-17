package com.yusril.githubuserapp.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yusril.githubuserapp.BuildConfig
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val apiClient =  ApiConfig.getApiService()

    fun loadSearchResult(username: String): LiveData<ArrayList<User>> {
        val users = MutableLiveData<ArrayList<User>>()
        apiClient.searchUser(BuildConfig.GITHUB_API_KEY, username).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
//                    for (user in response.body()?.items) {
//                        users.add(user)
//                    }
                    response.body().let { users.postValue(it?.items) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("RETROFIT", "onFailure: ${t.message.toString()}")
            }

        })

        return users
    }
}