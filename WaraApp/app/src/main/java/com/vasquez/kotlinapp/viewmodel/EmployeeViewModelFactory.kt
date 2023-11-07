package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasquez.kotlinapp.storage.EmployeeRepository

/**
 * Created by Eduardo Medina on 5/3/21.
 */
class EmployeeViewModelFactory(private val repository: EmployeeRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeeViewModel(repository) as T
    }
}