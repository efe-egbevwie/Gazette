package com.io.gazette.di

import android.content.Context
import androidx.room.Room
import com.io.gazette.data.local.NewsDatabase
import com.io.gazette.data.local.dao.NewsDao
import com.io.gazette.data.local.migrations.MIGRATION_2_3
import com.io.gazette.data.local.migrations.MIGRATION_3_4
import com.io.gazette.data.repositories.ReadLaterListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            context = appContext,
            NewsDatabase::class.java,
            name = "news_database.db"
        )
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .build()
    }

    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

    @Provides
    fun provideReadingListRepo(newsDao: NewsDao):ReadLaterListRepository{
        return ReadLaterListRepository(newsDao)
    }
}