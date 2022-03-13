package com.io.gazette.data.api.mappers

interface APiToEntityMapper<E, D> {
    fun mapToDomain(apiEntity: E):D
}