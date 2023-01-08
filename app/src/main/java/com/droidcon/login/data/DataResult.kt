package com.droidcon.login.data

interface DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>
    data class Error(
        val message: String,
        val networkError: Boolean = false,
        val exc: Throwable? = null
    ): DataResult<Nothing>
}