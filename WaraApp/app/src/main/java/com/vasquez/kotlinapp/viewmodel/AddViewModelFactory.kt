package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasquez.kotlinapp.storage.EmployeeRepository


class AddViewModelFactory(private val repository: EmployeeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddEmployeeViewModel(repository) as T
    }
}