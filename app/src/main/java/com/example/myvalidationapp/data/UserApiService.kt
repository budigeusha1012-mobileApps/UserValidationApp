package com.example.myvalidationapp.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("customers")
    suspend fun createUser(

        @Body user: User

    ): Response<User>
}