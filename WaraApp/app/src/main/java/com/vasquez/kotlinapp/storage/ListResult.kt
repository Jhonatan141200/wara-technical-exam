package com.vasquez.kotlinapp.storage

/**
 * @author Vasquez Reyna J
 */
sealed class ListResult<out T> {
    data class Success<T>(val data: List<T>) : ListResult<T>()
    data class Failure(val exception: Exception) : ListResult<Nothing>()
}