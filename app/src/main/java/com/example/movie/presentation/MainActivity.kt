package com.example.movie.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movie.Screen
import com.example.movie.presentation.movie_detail.MovieDetail
import com.example.movie.presentation.movie_list.MovieListScreen
import com.example.movie.ui.theme.MovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MovieTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)

                MovieApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val navController = rememberNavController()
    Scaffold() { innerPadding ->
        NavHostScreen(navController, innerPadding)
    }
}

@Composable
private fun NavHostScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController,
        startDestination = Screen.MovieListScreen.route,
    ) {
        composable(Screen.MovieListScreen.route) {
            MovieListScreen(
                navController = navController
            )
        }
        composable(Screen.MovieDetailScreen.route + "/{movieId}") {
            MovieDetail()
        }
    }
}