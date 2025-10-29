package com.example.filmrehberi.data.repository

import com.example.filmrehberi.data.local.MovieDao
import com.example.filmrehberi.data.local.MovieEntity
import com.example.filmrehberi.data.model.MovieDetail
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

            val movieEntities= response.Search.map { searchResult ->
                MovieEntity(
                    imdbID = searchResult.imdbID,
                    title = searchResult.Title,
                    year = searchResult.Year,
                    posterUrl = searchResult.Poster
                )

            }
            movieDao.clearMovies()

            movieDao.insertMovies(movieEntities)

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun getMovieDetail(imdbId: String): MovieDetail{
        return apiService.getMovieDetail(imdbId)
    }
}