package com.vasquez.kotlinapp.model

import java.io.Serializable

/**
 * @author Vasquez Reyna J
 */
data class Employee(val id: String, val firstName: String,val lastName: String, val dni: String, val age: Int) :
    Serializable {
    override fun toString(): String {
        return "Note(id=$id, firstName=$firstName, lastName=$lastName, dni=$dni age $age))"
    }
}
data class User(
    val id: String, val username: String,
    val firstName: String, val lastName: String, val password: String
) : Serializable