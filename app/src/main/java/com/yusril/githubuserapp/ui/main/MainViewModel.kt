package com.yusril.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.RemoteRepository
import com.yusril.githubuserapp.vo.Resource
import kotlinx.coroutines.*

class MainViewModel(): ViewModel() {
    private val repository: RemoteRepository = RemoteRepository()

    fun setSearchResult(username: String): LiveData<Resource<ArrayList<User>>> = repository.loadSearchResult(username)
}