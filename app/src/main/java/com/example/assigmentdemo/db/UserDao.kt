package com.example.assigmentdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEnitity: List<UserEnitity>)
    @Query("SELECT * FROM UserEnitity")
     fun getUser():LiveData<List<UserEnitity>>
}