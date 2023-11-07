package com.vasquez.kotlinapp.storage

import com.vasquez.kotlinapp.extensions.showCurrentThread
import com.vasquez.kotlinapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Vasquez Reyna J
 */
class AuthenticationRepository(private val authenticationDataSource: AuthenticationDataSource) {

    suspend fun login(username: String, password: String): ObjectResult<User> = withContext(Dispatchers.IO) {
        showCurrentThread()
        when (val result = authenticationDataSource.login(username, password)) {
            is ObjectResult.Success -> {
                ObjectResult.Success(result.data.toUser())
            }
            is ObjectResult.Failure -> {
                ObjectResult.Failure(result.exception)
            }
        }
    }
}