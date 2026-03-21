package com.example.wewatch.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.AddScreen
import com.example.wewatch.screens.MainScreen
import com.example.wewatch.screens.SearchScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    movies: List<MovieEntity>,
    searchResults: List<MovieEntity>,
    onAddMovie: (MovieEntity) -> Unit,
    onDeleteMovie: (List<MovieEntity>) -> Unit,
    onSearchMovies: (String, String?) -> Unit,
    onSearchSingleMovie: (String, String?, (MovieEntity?) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var currentSelectedMovie by remember { mutableStateOf<MovieEntity?>(null) }

    NavHost(navController, startDestination = "main") {

        composable("main") {

            MainScreen(
                movies = movies,
                onDeleteMovie = onDeleteMovie,
                onAddClick = {
                    currentSelectedMovie = null  // Сбрасываем при переходе
                    navController.navigate("add")
                }
            )
        }

        composable("add") {
            AddScreen(
                selectedMovie = currentSelectedMovie,
                // search all
                onSearchAll = { title, year ->
                    if (title.isNotBlank()) {
                        onSearchMovies(title, year)
                        navController.navigate("search")
                    }

                },

                // search single movie
                onSearchSingle = { title, year, onResult ->
                    scope.launch {
                        onSearchSingleMovie(title, year) { movie ->
                            currentSelectedMovie = movie
                            onResult(movie)
                        }
                    }
                },

                // add movie
                onAddMovie = { movie ->
                    onAddMovie(movie)
                    navController.popBackStack()
                }
            )
        }

        composable("search") {

            SearchScreen(
                movies = searchResults,
                onMovieClick = { movie ->

                    onAddMovie(movie)
                    navController.popBackStack()

                }
            )
        }
    }
}