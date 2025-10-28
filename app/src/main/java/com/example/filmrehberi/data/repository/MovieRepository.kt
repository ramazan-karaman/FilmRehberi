package com.example.filmrehberi.data.repository

import com.example.filmrehberi.data.local.MovieDao
import com.example.filmrehberi.data.local.MovieEntity
import com.example.filmrehberi.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val apiService: ApiService, private val movieDao: MovieDao) {

    fun getMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    suspend fun searchMovies(query: String){
        try {
            val response= apiService.searchMovies(searchQuery = query, page = 1)

            val movieEntities= response.searchResults.map { searchResult ->
                MovieEntity(
                    imdbID = searchResult.imdbID,
                    title = searchResult.title,
                    year = searchResult.year,
                    posterUrl = searchResult.posterUrl
                )

            }
            movieDao.clearMovies()

            movieDao.insertMovies(movieEntities)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}