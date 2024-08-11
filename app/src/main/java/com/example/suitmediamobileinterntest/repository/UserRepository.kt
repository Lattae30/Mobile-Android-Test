package com.example.suitmediamobileinterntest.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.suitmediamobileinterntest.data.UserPagingSource
import com.example.suitmediamobileinterntest.data.api.ApiConfig
import com.example.suitmediamobileinterntest.data.api.ApiService
import com.example.suitmediamobileinterntest.retrofit.response.DataItem

class UserRepository private constructor(
    private val apiService: ApiService
){

    fun getUserData(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}