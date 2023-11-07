package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasquez.kotlinapp.storage.AuthenticationRepository

/**
 * Created by Eduardo Medina on 5/3/21.
 */
class LogInViewModelFactory(private val repository: AuthenticationRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogInViewModel(repository) as T
    }
}