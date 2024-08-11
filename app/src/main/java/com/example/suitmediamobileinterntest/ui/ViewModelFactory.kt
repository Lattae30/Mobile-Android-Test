package com.example.suitmediamobileinterntest.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suitmediamobileinterntest.di.Injection
import com.example.suitmediamobileinterntest.repository.UserRepository
import com.example.suitmediamobileinterntest.ui.ThirdActivity.ThirdActViewModel

class ViewModelFactory (private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdActViewModel::class.java)){
            return ThirdActViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideUserRepository()).also { INSTANCE = it }
            }
        }
    }
}