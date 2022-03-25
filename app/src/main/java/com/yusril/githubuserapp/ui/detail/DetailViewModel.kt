package com.yusril.githubuserapp.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.model.UserDetail
import com.yusril.githubuserapp.data.source.local.LocalRepository
import com.yusril.githubuserapp.data.source.remote.RemoteRepository
import com.yusril.githubuserapp.vo.Resource

class DetailViewModel(application: Application) : ViewModel() {
    private val repository: RemoteRepository = RemoteRepository()
    private val localRepository: LocalRepository = LocalRepository(application)

    fun getUser(username: String) : LiveData<Resource<UserDetail>> = repository.getUser(username)

    fun getFavoriteByUsername(username: String) : LiveData<List<User>> = localRepository.getFavoriteByUsername(username)

    fun insertUser(user: User) = localRepository.insert(user)

    fun deleteUser(user: User) = localRepository.delete(user)
}