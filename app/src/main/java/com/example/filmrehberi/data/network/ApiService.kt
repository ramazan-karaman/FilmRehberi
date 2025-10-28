package com.example.filmrehberi.data.network

import retrofit2.http.Query
import com.example.filmrehberi.data.model.MovieDetail
import com.example.filmrehberi.data.model.MovieSearchResponse
import retrofit2.http.GET

interface ApiService {

    companion object{
        const val BASE_URL = "https://www.omdbapi.com/"
    }

    @GET("/")
    suspend fun searchMovies(
        @Query(value = "s") searchQuery: String,
        @Query(value = "page") page: Int
    ) : MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetail(
        @Query("i") imdbID: String
    ): MovieDetail
}