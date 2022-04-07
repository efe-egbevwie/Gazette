package com.io.gazette.di

import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.useCases.*

class UseCasesModule(nytRepository: NytRepository) {

    val mainViewModelUseCases by lazy {
        MainViewModelUseCases(
            businessNewsUseCase,
            healthNewsUseCase,
            sportsNewsUseCase,
            worldNewsUseCase
        )
    }

    private val businessNewsUseCase by lazy { GetBusinessNewsUseCase(nytRepository) }

    private val healthNewsUseCase by lazy { GetHealthNewsUseCase(nytRepository) }

    private val sportsNewsUseCase by lazy { GetSportsNewsUseCase(nytRepository) }

    private val worldNewsUseCase by lazy { GetWorldNewsUseCase(nytRepository) }
}