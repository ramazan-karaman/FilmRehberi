package com.example.filmrehberi.data.model

import com.squareup.moshi.Json

data class MovieSearchResult(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String
)
