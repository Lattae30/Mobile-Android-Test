package com.example.suitmediamobileinterntest.ui.ThirdActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.suitmediamobileinterntest.repository.UserRepository
import com.example.suitmediamobileinterntest.retrofit.response.DataItem

class ThirdActViewModel(private val repository: UserRepository) : ViewModel() {
    fun getUsers(): LiveData<PagingData<DataItem>>{
        return repository.getUserData().cachedIn(viewModelScope)
    }
}