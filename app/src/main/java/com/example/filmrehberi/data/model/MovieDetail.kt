package com.example.filmrehberi.data.model

import com.squareup.moshi.Json

data class MovieDetail(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String,
    val Plot: String,
    val imdbRating: String
)