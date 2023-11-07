package com.vasquez.kotlinapp.storage.remote

import com.vasquez.kotlinapp.model.Employee
import com.vasquez.kotlinapp.model.User

/**
 * @author Vasquez Reyna J
 */

data class UserDTO(
    val id: String?,
    val username: String?,
    val password: String?,
    val firstname: String?,
    val lastname: String?
) {
    fun toUser():User = User(id?:"",username?:"",firstname?:"",
    lastname?:"",password?:"")
}

data class EmployeeDTO(
    val id: String?,
    val firstname: String?,
    val lastname: String?,
    val dni: String?,
    val age: Int?
) {
    fun toEmployee():Employee = Employee(id?:"",firstname?:"",lastname?:"",
    dni?:"", age?:0)
}

//request
data class LogInRaw(val username: String?, val password: String?)
data class EmployeeRaw(val firstname: String?,
                     val lastname: String?,
                     val dni: String?,
                     val age: Int?)

//response

data class LogInResponse(val msg: String?, val status: Int?, val data: UserDTO?)
//class LogInResponse(msg:String?,status:Int?,val data:UserDTO?):BaseResponse(msg,status)

data class EmployeeResponse(val msg: String?, val status: Int?, val data: List<EmployeeDTO>?)
data class ResponseOfConsult(val msg: String?, val status: Int?)
