package com.io.gazette.data.repositories

import com.io.gazette.data.api.NytApi
import com.io.gazette.data.api.models.GetTopStoriesByCategoryResponse

class NytRepository(private val nytApi: NytApi) {

    suspend fun getWorldNews():GetTopStoriesByCategoryResponse{
        return nytApi.getTopWorldNews()
    }

    suspend fun getBusinessNews():GetTopStoriesByCategoryResponse{
        return nytApi.getTopBusinessNews()
    }

    suspend fun getSportsNews():GetTopStoriesByCategoryResponse{
        return nytApi.getTopSportsNews()
    }

    suspend fun getHealthNews():GetTopStoriesByCategoryResponse{
        return nytApi.getTopHealthNews()
    }
}