package com.example.assigmentdemo.repository

import androidx.lifecycle.LiveData
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.db.UserEnitity

class UserRepository(val db: UserDataBase) {

    suspend fun insert(userEnitity: UserEnitity) = db.userDao.insertUser(userEnitity)

    fun getUser(): LiveData<UserEnitity> {
        return db.userDao.getUser()
    }
}