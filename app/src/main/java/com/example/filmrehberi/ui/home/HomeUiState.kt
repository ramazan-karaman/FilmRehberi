package com.example.filmrehberi.ui.home

import com.example.filmrehberi.data.local.MovieEntity

data class HomeUiState(
    val movies: List<MovieEntity> = emptyList(),
    val searchQuery: String = "batman",
    val isLoading: Boolean = false
)