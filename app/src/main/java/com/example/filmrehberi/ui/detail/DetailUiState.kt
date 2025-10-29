package com.example.filmrehberi.ui.detail

import com.example.filmrehberi.data.model.MovieDetail

data class DetailUiState(
    val isLoading: Boolean= true,
    val movie: MovieDetail?= null,
    val error: String?= null
)
