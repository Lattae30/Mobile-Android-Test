package com.example.suitmediamobileinterntest.di

import com.example.suitmediamobileinterntest.data.api.ApiConfig
import com.example.suitmediamobileinterntest.repository.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository{
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}