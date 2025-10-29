package com.example.filmrehberi.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmrehberi.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())

    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
    }

    private fun loadMovieDetail() {
        val imdbId: String? = savedStateHandle.get("movieId")

        if (imdbId != null) {
            viewModelScope.launch {
                try {
                    val movieDetail = movieRepository.getMovieDetail(imdbId)
                    _uiState.value = DetailUiState(isLoading = false, movie = movieDetail)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _uiState.value = DetailUiState(
                        isLoading = false,
                        movie = null,
                        error = e.message ?: "Bilinmeyen hata"
                    )
                }
            }
        } else {
            _uiState.value = DetailUiState(isLoading = false, error = "Film ID'si bulunamadı")
        }
    }
}