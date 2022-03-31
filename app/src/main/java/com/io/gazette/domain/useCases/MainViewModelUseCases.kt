package com.io.gazette.domain.useCases

import com.io.gazette.data.repositories.NytRepository

data class MainViewModelUseCases(
    val getBusinessUseCase: GetBusinessNewsUseCase,
    val getHealthNewsUseCase: GetHealthNewsUseCase,
    val getSportsNewsUseCase: GetSportsNewsUseCase,
    val getWorldNewsUseCase: GetWorldNewsUseCase
)