package com.vasquez.kotlinapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasquez.kotlinapp.model.Employee
import com.vasquez.kotlinapp.storage.ListResult
import com.vasquez.kotlinapp.storage.EmployeeRepository
import kotlinx.coroutines.launch

/**
 * Created by Eduardo Medina on 5/3/21.
 */
class EmployeeViewModel(private val noteRepository: EmployeeRepository) : ViewModel() {

    private var _onError = MutableLiveData<String>()
    val onError: LiveData<String> = _onError

    private var _notes = MutableLiveData<List<Employee>>()
    val notes: LiveData<List<Employee>> = _notes

    /*init {
        fetchNotesByUser("")
    }*/
    fun fetchEmployees() {
        viewModelScope.launch {
            when (val result = noteRepository.fetchEmployees()) {
                is ListResult.Success -> {
                    _notes.value = result.data
                }
                is ListResult.Failure -> {
                    _onError.value = result.exception.message
                }
            }
        }
    }

}