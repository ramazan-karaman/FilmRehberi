package com.example.filmrehberi.data.model

import com.squareup.moshi.Json

data class MovieSearchResult(
    @field:Json(name = "imdbID") val imdbID: String,
    @field:Json(name = "Title") val title: String,
    @field:Json(name = "Year") val year: String,
    @field:Json(name = "Poster") val posterUrl: String
)
