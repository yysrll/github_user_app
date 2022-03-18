package com.yusril.githubuserapp.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yusril.githubuserapp.BuildConfig
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.response.UserResponse
import com.yusril.githubuserapp.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository {
    private val apiClient =  ApiConfig.getApiService()

    fun loadSearchResult(username: String): LiveData<Resource<ArrayList<User>>> {
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.searchUser(BuildConfig.GITHUB_API_KEY, username).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                users.value = Resource.loading()
                if (response.isSuccessful) {
                    response.body().let {
                        users.value = Resource.success(response.body()?.items)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }

        })

        return users
    }
}