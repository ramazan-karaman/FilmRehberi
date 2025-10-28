package com.example.filmrehberi.data.model

import com.squareup.moshi.Json

data class MovieSearchResponse(
    @field:Json(name= "Search") val searchResults: List<MovieSearchResult>,
    @field:Json(name= "totalResults") val totalResult: String,
    @field:Json(name= "Response") val response: String
)
