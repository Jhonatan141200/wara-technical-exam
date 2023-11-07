package com.vasquez.kotlinapp.storage


import com.vasquez.kotlinapp.model.Employee
import com.vasquez.kotlinapp.storage.remote.EmployeeRaw
import com.vasquez.kotlinapp.storage.remote.ResponseOfConsult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Vasquez Reyna J
 */
class EmployeeRepository(private val employeeDataSource: EmployeeDataSource) {


    suspend fun fetchEmployees(): ListResult<Employee> = withContext(Dispatchers.IO) {
        when (val result = employeeDataSource.employees()) {
            is ListResult.Success -> {
                ListResult.Success(result.data.map {
                    it.toEmployee()
                })
            }
            is ListResult.Failure -> {
                ListResult.Failure(result.exception)
            }
        }
    }

    suspend fun saveEmployee(employeeRaw: EmployeeRaw): ObjectResult<ResponseOfConsult> = withContext(Dispatchers.IO) {
        when (val result = employeeDataSource.saveEmployee(employeeRaw)) {
            is ObjectResult.Success -> {
                ObjectResult.Success(result.data)
            }
            is ObjectResult.Failure -> {
                ObjectResult.Failure(result.exception)
            }
        }
    }



}