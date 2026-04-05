package com.example.wewatch.screens.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.wewatch.viewmodel.AddViewModel
import com.example.wewatch.viewmodel.MainViewModel
import com.example.wewatch.viewmodel.SearchViewModel

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel,
    addViewModel: AddViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    var selectedMovieFromSearch by remember { mutableStateOf<MovieEntity?>(null) }

    NavHost(navController, startDestination = "main",  modifier = modifier) {
        composable(
            route = "main",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            MainScreen(
                movies = mainViewModel.movies,
                onDeleteMovie = { moviesList -> mainViewModel.deleteMovies(moviesList) },
                onAddClick = {
                    selectedMovieFromSearch = null  // Сбрасываем при переходе
                    navController.navigate("add") {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = "add",
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            AddScreen(
                selectedMovie = selectedMovieFromSearch,
                // search all
                onSearchAll = { title, year ->
                    if (title.isNotBlank()) {
                        searchViewModel.searchMovies(title, year)
                        navController.navigate("search/$title/${year ?: ""}")
                    }

                },
                // add movie
                onAddMovie = { movie ->
                    addViewModel.addMovie(movie)
                    mainViewModel.loadMoviesFromDb()
                    selectedMovieFromSearch = null
                    navController.popBackStack()
                },
                onBack = {
                    if (selectedMovieFromSearch != null) {
                        val movie = selectedMovieFromSearch
                        selectedMovieFromSearch = null
                        navController.navigate("search/${movie!!.title}/${movie.year}") {
                            launchSingleTop = true
                        }
                    } else {
                        navController.popBackStack()
                    }
                }

            )
        }

        composable(
            route = "search/{title}/{year}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val year = backStackEntry.arguments?.getString("year")?.takeIf { it.isNotBlank() }

            // Загружаем результаты при входе
//            if (searchResults.isEmpty() && title.isNotBlank()) {
//                onSearchMovies(title, year)
//            }

            LaunchedEffect(title, year) {
                if (title.isNotBlank()) {
                    searchViewModel.searchMovies(title, year)
                }
            }

            SearchScreen(
                movies = searchViewModel.searchResults,
                onMovieClick = { movie ->
                    // При клике на фильм сохраняем его и возвращаемся в AddScreen
                    selectedMovieFromSearch = movie
                    navController.popBackStack()
                }
            )
        }
    }
}