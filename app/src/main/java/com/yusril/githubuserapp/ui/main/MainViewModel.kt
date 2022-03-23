package com.yusril.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.RemoteRepository
import com.yusril.githubuserapp.vo.Resource

class MainViewModel: ViewModel() {
    private val repository: RemoteRepository = RemoteRepository()

    fun setSearchResult(username: String): LiveData<Resource<ArrayList<User>>> = repository.loadSearchResult(username)
}