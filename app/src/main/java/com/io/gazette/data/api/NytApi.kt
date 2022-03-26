package com.io.gazette.data.api

import com.io.gazette.BuildConfig
import com.io.gazette.data.api.models.GetTopStoriesByCategoryResponse
import com.io.gazette.domain.BUSINESS_NEWS
import com.io.gazette.domain.HEALTH_NEWS
import com.io.gazette.domain.SPORTS_NEWS
import com.io.gazette.domain.WORLD_NEWS
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApi {

    @GET(WORLD_NEWS)
    suspend fun getTopWorldNews(@Query("api-key") apiKey:String = BuildConfig.NYT_API_KEY):GetTopStoriesByCategoryResponse

    @GET(BUSINESS_NEWS)
    suspend fun getTopBusinessNews(@Query("api-key") apiKey:String = BuildConfig.NYT_API_KEY):GetTopStoriesByCategoryResponse

    @GET(SPORTS_NEWS)
    suspend fun getTopSportsNews(@Query("api-key") apiKey:String = BuildConfig.NYT_API_KEY):GetTopStoriesByCategoryResponse

    @GET(HEALTH_NEWS)
    suspend fun getTopHealthNews(@Query("api-key") apiKey:String = BuildConfig.NYT_API_KEY):GetTopStoriesByCategoryResponse
}