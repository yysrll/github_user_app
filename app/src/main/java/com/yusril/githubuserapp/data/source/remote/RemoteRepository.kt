package com.yusril.githubuserapp.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yusril.githubuserapp.BuildConfig
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.model.UserDetail
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

    fun getUser(username: String): LiveData<Resource<UserDetail>> {
        val user = MutableLiveData<Resource<UserDetail>>()
        apiClient.getUser(BuildConfig.GITHUB_API_KEY, username).enqueue(object : Callback<UserDetail>{
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                user.value = Resource.loading()
                if (response.isSuccessful) {
                    response.body().let {
                        user.value = Resource.success(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                user.value = Resource.error(t.message.toString())
            }

        })

        return user
    }


    fun getFollowers(username: String): LiveData<Resource<ArrayList<User>>> {
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.getFollowers(BuildConfig.GITHUB_API_KEY, username).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                users.value = Resource.loading()
                if (response.isSuccessful) {
                    response.body().let {
                        users.value = Resource.success(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }

        })

        return users
    }


    fun getFollowing(username: String): LiveData<Resource<ArrayList<User>>> {
        val users = MutableLiveData<Resource<ArrayList<User>>>()
        apiClient.getFollowing(BuildConfig.GITHUB_API_KEY, username).enqueue(object : Callback<ArrayList<User>>{
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                users.value = Resource.loading()
                if (response.isSuccessful) {
                    response.body().let {
                        users.value = Resource.success(response.body())
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                users.value = Resource.error(t.message.toString())
            }

        })

        return users
    }
}