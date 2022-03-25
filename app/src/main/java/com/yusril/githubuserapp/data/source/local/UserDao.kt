package com.yusril.githubuserapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yusril.githubuserapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("Select * from user ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<User>>

    @Query("Select * from user  Where login = :username")
    fun getFavoriteByUsername(username: String) : LiveData<List<User>>
}