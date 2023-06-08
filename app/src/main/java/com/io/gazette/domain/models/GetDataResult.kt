package com.io.gazette.domain.models

sealed class GetDataResult<out T:Any>{
    data class Failure(val exception:Exception):GetDataResult<Nothing>()
    data class Success<out T: Any>(val data:T? = null):GetDataResult<T>()
}

