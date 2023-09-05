package com.storm.feedv2.di

import android.content.Context
import androidx.room.Room
import com.storm.feedv2.data.local.FeedDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FeedDatabase {
        return Room.databaseBuilder(
            context,
            FeedDatabase::class.java,
            "feed_database"
        ).build()
    }
}