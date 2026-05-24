package com.example.myvalidationapp.data

import javax.inject.Inject

class UserRepository @Inject constructor(

    private val apiService: UserApiService

) {
    suspend fun createUser(user: User) =

        apiService.createUser(user)
}