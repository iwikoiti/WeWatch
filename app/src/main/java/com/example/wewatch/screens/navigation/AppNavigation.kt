package com.example.wewatch.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.AddScreen
import com.example.wewatch.screens.MainScreen
import com.example.wewatch.screens.SearchScreen

@Composable
fun AppNavigation(
    movies: List<MovieEntity>
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(
                movies = movies,
                onAddClick = {
                    navController.navigate("add")
                }
            )
        }

        composable("add") {
            AddScreen(
                onSearchClick = { title, year ->
                    navController.navigate("search")
                },
                onAddClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("search") {
            SearchScreen(
                movies = emptyList(),
                onMovieClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}