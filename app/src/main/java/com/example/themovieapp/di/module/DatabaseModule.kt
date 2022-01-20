package com.example.themovieapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.themovieapp.data.db.AppDatabase
import com.example.themovieapp.data.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext ctx: Context) : AppDatabase {
        return Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "Movie"
        ).build()
    }
}