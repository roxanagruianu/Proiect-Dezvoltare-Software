package com.example.myapplication.data.model

import android.util.Log

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        val user = userDao.authenticate(email, password)
        Log.d("UserRepository", "login() returned: $user")
        return user
    }

    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }
}
