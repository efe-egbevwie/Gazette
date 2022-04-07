package com.io.gazette.di

import com.io.gazette.data.api.buildNytAPi
import com.io.gazette.data.repositories.NytRepository

class NetworkModule {

    private val nytApi by lazy { buildNytAPi() }

    val nytRepository by lazy { NytRepository(nytApi) }

}