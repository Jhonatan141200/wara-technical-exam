package com.vasquez.kotlinapp.storage

import com.vasquez.kotlinapp.storage.remote.EmployeeDTO
import com.vasquez.kotlinapp.storage.remote.EmployeeRaw
import com.vasquez.kotlinapp.storage.remote.ResponseOfConsult
import com.vasquez.kotlinapp.storage.remote.UserDTO


/**
 * @author Vasquez Reyna J
 */
interface EmployeeDataSource {

    suspend fun employees():ListResult<EmployeeDTO>
    suspend fun saveEmployee(employeeRaw: EmployeeRaw):ObjectResult<ResponseOfConsult>

}