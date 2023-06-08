package com.io.gazette.di

import android.content.Context
import com.io.gazette.data.remote.api.NytApi
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @Provides
    @Singleton
    fun provideKotlinXSerializationJsonConfig(): Json {
        return Json {
            coerceInputValues = true; prettyPrint = true;ignoreUnknownKeys =
            true; isLenient; coerceInputValues = true; explicitNulls = false
        }
    }

    @Provides
    @Singleton
    fun provideRetrofitClientInstance(
        kotlinxJsonConfig: Json,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(kotlinxJsonConfig.asConverterFactory(contentType))
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsApi(retrofitClient: Retrofit): NytApi {
        return retrofitClient.create(NytApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDeviceConnectivityUtil(@ApplicationContext context: Context): DeviceConnectivityUtil {
        return DeviceConnectivityUtil(context)
    }

    @Provides
    @Singleton
    fun provideNYTRepository(
        newsDao: NewsDao,
        deviceConnectivityUtil: DeviceConnectivityUtil,
        nytApi: NytApi
    ): NytRepository {
        return NytRepository(nytApi, newsDao, deviceConnectivityUtil)
    }

}