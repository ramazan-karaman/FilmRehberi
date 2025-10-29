package com.example.filmrehberi.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onNavigateBack: ()-> Unit,
                 viewModel: DetailViewModel= hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = uiState.movie?.Title ?: "Detay Yükleniyor...")
                },
                navigationIcon={
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri Git"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            if (uiState.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }else if (uiState.error!=null){
                Text(text = uiState.error!!, modifier = Modifier.align(Alignment.Center))
            }else{
                uiState.movie?.let { movie->
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        AsyncImage(model = movie.Poster,
                            contentDescription = movie.Title,
                            modifier = Modifier.fillMaxWidth().aspectRatio(2f/3f),
                            contentScale = ContentScale.Crop)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = movie.Title, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "⭐ ${movie.imdbRating}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = movie.Plot, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

    }
}