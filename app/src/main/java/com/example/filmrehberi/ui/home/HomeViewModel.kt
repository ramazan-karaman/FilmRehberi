package com.example.filmrehberi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmrehberi.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _searchQuery = MutableStateFlow("batman")

    private val _isLoading = MutableStateFlow(false)

    private val _moviesFlow = movieRepository.getMovies()

    val uiState: StateFlow<HomeUiState> = combine(
        _searchQuery,
        _isLoading,
        _moviesFlow
    ) { query, loading, movies ->
        HomeUiState(
            searchQuery = query,
            isLoading = loading,
            movies = movies
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState()
    )

    init {
        performSearch()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        val query = _searchQuery.value
        if (query.isBlank()) return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                movieRepository.searchMovies(query)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}