package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasquez.kotlinapp.model.User
import com.vasquez.kotlinapp.storage.AuthenticationRepository
import com.vasquez.kotlinapp.storage.ObjectResult
import kotlinx.coroutines.launch

/**
 * Created by Eduardo Medina on 5/3/21.
 */
class LogInViewModel(private val repository: AuthenticationRepository):ViewModel() {

    val onError = SingleLiveEvent<String>()
    val isAuthenticated = SingleLiveEvent<User>()

    fun login(username:String,password:String) {
        viewModelScope.launch {
            when(val result = repository.login(username,password)){
                is ObjectResult.Success -> {
                    //_isAuthenticated.value = result.data
                    isAuthenticated.value = result.data
                }

                is ObjectResult.Failure -> {
                    //_onError.value = result.exception.message
                    onError.value = result.exception.message
                }
            }
        }
    }
}