package com.example.assigmentdemo.viewmodelfactorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.viewmodels.UserViewModel

class UserFactory(val userRepository: UserRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("UnKnown user viewmodel")
    }


}