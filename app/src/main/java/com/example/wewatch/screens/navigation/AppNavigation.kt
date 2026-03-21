package com.example.wewatch.screens.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var selectedMovieFromSearch by remember { mutableStateOf<MovieEntity?>(null) }

    // Для отладки - смотрим текущий стек
    navController.addOnDestinationChangedListener { _, destination, _ ->
        Log.d("Navigation", "Current destination: ${destination.route}")
    }

    NavHost(navController, startDestination = "main",  modifier = modifier) {
        composable("main") {
            Log.d("Navigation", "MainScreen composable")
            MainScreen(
                movies = movies,
                onDeleteMovie = onDeleteMovie,
                onAddClick = {
                    selectedMovieFromSearch = null  // Сбрасываем при переходе
                    navController.navigate("add")
                }
            )
        }

        composable("add") {
            Log.d("Navigation", "AddScreen composable, selectedMovie = $selectedMovieFromSearch")
            AddScreen(
                selectedMovie = selectedMovieFromSearch,
                // search all
                onSearchAll = { title, year ->
                    if (title.isNotBlank()) {
                        Log.d("Navigation", "Navigating to search from add, current back stack: ${navController.previousBackStackEntry?.destination?.route}")
                        onSearchMovies(title, year)
                        navController.navigate("search/$title/${year ?: ""}")
                    }

                },
                // add movie
                onAddMovie = { movie ->
                    onAddMovie(movie)
                    selectedMovieFromSearch = null
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "search/{title}/{year}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.takeIf { it.isNotBlank() }

            Log.d("Navigation", "SearchScreen composable, title=$title, year=$year")
            Log.d("Navigation", "SearchScreen composable, back stack entry: ${backStackEntry.destination.route}")

            // Загружаем результаты при входе
            if (searchResults.isEmpty() && title.isNotBlank()) {
                onSearchMovies(title, year)
            }

            SearchScreen(
                movies = searchResults,
                onMovieClick = { movie ->
                    // При клике на фильм сохраняем его и возвращаемся в AddScreen
                    selectedMovieFromSearch = movie
                    navController.popBackStack()
                }
            )
        }
    }
}