package com.example.filmrehberi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val posterUrl: String

)
