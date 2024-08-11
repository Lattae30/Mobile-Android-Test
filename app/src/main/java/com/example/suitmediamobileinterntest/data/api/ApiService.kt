package com.example.suitmediamobileinterntest.data.api

import com.example.suitmediamobileinterntest.retrofit.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): UserResponse
}