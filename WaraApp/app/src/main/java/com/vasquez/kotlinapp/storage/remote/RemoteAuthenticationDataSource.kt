package com.vasquez.kotlinapp.storage.remote

import com.vasquez.kotlinapp.storage.AuthenticationDataSource
import com.vasquez.kotlinapp.storage.ObjectResult

/**
 * @author Vasquez Reyna J
 */
class RemoteAuthenticationDataSource(private val noteApiClient: NoteApiClient) :
    AuthenticationDataSource {

    private val servicesApi by lazy {
        noteApiClient.build()
    }

    //Using courtines
    override suspend fun login(
        username: String,
        password: String
    ): ObjectResult<UserDTO> {
        return try {
            val raw = LogInRaw(username, password)
            val response = servicesApi?.login(raw)
            if (response?.isSuccessful == true) {
                response.body()?.data?.let {
                    ObjectResult.Success(it)
                } ?: run {
                    when(response.body()?.msg) {
                        "Usuario no encontrado" -> ObjectResult.Failure(Exception("Usuario incorrecto"))
                        "Contraseña incorrecta" -> ObjectResult.Failure(Exception("Contraseña incorrecta"))
                        else -> ObjectResult.Failure(Exception("Ocurrió un error"))
                    }
                }
            } else {
                ObjectResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (exception: Exception) {
            ObjectResult.Failure(exception)
        }
    }

}