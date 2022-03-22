package com.yusril.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.UserDetail
import com.yusril.githubuserapp.data.source.remote.RemoteRepository
import com.yusril.githubuserapp.vo.Resource

class DetailViewModel(): ViewModel() {
    private val repository: RemoteRepository = RemoteRepository()

    fun getUser(username: String) : LiveData<Resource<UserDetail>> = repository.getUser(username)
}