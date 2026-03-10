package com.example.wewatch.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.components.MovieItem

@Composable
fun SearchScreen(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit
) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onCheckedChange = { }
            )
        }
    }
}