package com.vasquez.kotlinapp.storage

import com.vasquez.kotlinapp.storage.remote.UserDTO

/**
 * @author Vasquez Reyna J
 */
interface AuthenticationDataSource {

    suspend fun login(
        username: String, password: String): ObjectResult<UserDTO>
}