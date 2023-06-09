package com.io.gazette.di

import android.content.Context
import androidx.room.Room
import com.io.gazette.data.local.NewsDatabase
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.migrations.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            context = appContext,
            NewsDatabase::class.java,
            name = "news_database.db"
        )
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

}