package com.vasquez.kotlinapp.storage

/**
 * @author Vasquez Reyna J
 */
sealed class ObjectResult<out T> {
    data class Success<T>(val data: T) : ObjectResult<T>()
    data class Failure(val exception: Exception) : ObjectResult<Nothing>()
}