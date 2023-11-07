package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasquez.kotlinapp.storage.EmployeeRepository
import com.vasquez.kotlinapp.storage.ObjectResult
import com.vasquez.kotlinapp.storage.remote.EmployeeRaw
import kotlinx.coroutines.launch

class AddEmployeeViewModel(private val repository: EmployeeRepository): ViewModel() {

    val onError = SingleLiveEvent<String>()
    val onSuccess = SingleLiveEvent<String>()

    fun save(employeeRaw: EmployeeRaw) {
        viewModelScope.launch {
            when(val result = repository.saveEmployee(employeeRaw)){
                is ObjectResult.Success -> {
                    //_isAuthenticated.value = result.data
                    onSuccess.value = result.data.msg
                }

                is ObjectResult.Failure -> {
                    //_onError.value = result.exception.message
                    onError.value = result.exception.message
                }
            }
        }
    }
}