package com.yusril.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.githubuserapp.data.model.User
import com.yusril.githubuserapp.data.source.local.LocalRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val localRepository: LocalRepository = LocalRepository(application)

    fun getAllFavorites() : LiveData<List<User>> = localRepository.getAllFavorites()
}