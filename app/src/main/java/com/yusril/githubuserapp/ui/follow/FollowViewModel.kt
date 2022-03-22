package com.yusril.githubuserapp.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.RemoteRepository
import com.yusril.githubuserapp.vo.Resource

class FollowViewModel() : ViewModel() {
    private val repository : RemoteRepository = RemoteRepository()

    fun getFollowers(username: String) : LiveData<Resource<ArrayList<User>>> = repository.getFollowers(username)

    fun getFollowing(username: String) : LiveData<Resource<ArrayList<User>>> = repository.getFollowing(username)
}