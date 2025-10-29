package com.example.filmrehberi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmrehberi.ui.Screen
import com.example.filmrehberi.ui.detail.DetailScreen
import com.example.filmrehberi.ui.home.HomeScreen
import com.example.filmrehberi.ui.theme.FilmRehberiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmRehberiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home
                    ) {
                        composable(route = Screen.Home) {
                            HomeScreen(
                                onMovieClick = { imdbId ->
                                    navController.navigate("${Screen.Detail}/$imdbId")
                                }
                            )
                        }

                        composable(
                            route = Screen.DetailRoute,
                            arguments = listOf(
                                navArgument(Screen.MOVIE_ID_ARG) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            DetailScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}