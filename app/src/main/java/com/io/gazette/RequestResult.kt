package com.io.gazette

sealed class RequestResult<out T: Any> {}
    data class Success<out T: Any>(val data: T) : RequestResult<T>()
    data class Failure(val errorMessage:String):RequestResult<Nothing>()
    object Loading:RequestResult<Nothing>()

