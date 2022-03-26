package com.io.gazette.data.api

import com.io.gazette.domain.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}


fun buildRetrofitClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()


@OptIn(ExperimentalSerializationApi::class)
fun buildRetrofit(): Retrofit {
    val contentType = "application/json".toMediaType()
    val jsonConfig = Json {
        coerceInputValues = true; prettyPrint = true;ignoreUnknownKeys =
        true; isLenient; coerceInputValues = true; explicitNulls = false
    }

    return Retrofit.Builder()
        .client(buildRetrofitClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonConfig.asConverterFactory(contentType))
        .build()
}

fun buildNytAPi():NytApi = buildRetrofit().create(NytApi::class.java)