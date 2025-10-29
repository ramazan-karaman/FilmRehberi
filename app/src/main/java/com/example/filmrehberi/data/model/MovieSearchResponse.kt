package com.example.filmrehberi.data.model

import com.squareup.moshi.Json

data class MovieSearchResponse(
    val Search: List<MovieSearchResult>,
    val totalResults: String,
    val Response: String
)
