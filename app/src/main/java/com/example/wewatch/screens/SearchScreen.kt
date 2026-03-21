package com.example.wewatch.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.components.MovieItem

@Composable
fun SearchScreen(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit,
) {
    if (movies.isEmpty()) {
        // Пустое состояние
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Ничего не найдено")
        }
    } else {
        LazyColumn {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    checked = false,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onMovieClick(movie)
                        }
                    },
                    showCheckbox = false
                )
            }
        }
    }
}