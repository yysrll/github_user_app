package com.yusril.githubuserapp.data.source.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.yusril.githubuserapp.data.model.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavorites(): LiveData<List<User>> = mUserDao.getAllFavorite()

    fun getFavoriteByUsername(username: String): LiveData<List<User>> = mUserDao.getFavoriteByUsername(username)

    fun insert(user: User) = executorService.execute { mUserDao.insert(user) }

    fun delete(user: User) = executorService.execute { mUserDao.delete(user) }

    fun update(user: User) = executorService.execute { mUserDao.update(user) }

}