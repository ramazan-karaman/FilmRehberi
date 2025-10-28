package com.example.filmrehberi.di

import android.content.Context
import androidx.room.Room
import com.example.filmrehberi.data.local.MovieDao
import com.example.filmrehberi.data.local.MovieDatabase
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
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase{
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDatabase): MovieDao{
        return db.movieDao()
    }

}