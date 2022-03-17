package com.yusril.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.remote.RemoteRepository

class MainViewModel(): ViewModel() {
    private val repository: RemoteRepository = RemoteRepository()

    fun setSearchResult(username: String): LiveData<ArrayList<User>> = repository.loadSearchResult(username)
}