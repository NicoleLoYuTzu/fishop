package com.nicole.fishop.data

sealed class Result1<out R> {

    data class Success<out T>(val data: T) : Result1<T>()
    data class Fail(val error: String) : Result1<Nothing>()
    data class Error(val exception: Exception) : Result1<Nothing>()
    object Loading : Result1<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$data]"
            is Fail -> "Fail[error=$error]"
            is Error -> "Error[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of catalogType [Success] & holds non-null [Success.data].
 */
val Result1<*>.succeeded
    get() = this is Result1.Success && data != null
