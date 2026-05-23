package com.example.myvalidationapp.data

class UserRepository(
    private val apiService: UserApiService

) {
    suspend fun createUser(

        user: User

    ) = apiService.createUser(user)
}