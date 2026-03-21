package com.example.wewatch.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wewatch.R
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.components.MovieItem

@Composable
fun MainScreen(
    movies: List<MovieEntity>,
    onDeleteMovie: (List<MovieEntity>) -> Unit,
    onAddClick: () -> Unit
) {
    var selectedMovies by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        floatingActionButton = {
            Row {
                FloatingActionButton(
                    onClick = {
                        val toDelete = movies.filter { selectedMovies.contains(it.imdbId) }
                        onDeleteMovie(toDelete)
                        selectedMovies = emptySet()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_delete_24),  // название твоего файла
                        contentDescription = "Delete"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                FloatingActionButton(onClick = onAddClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),  // название твоего файла
                        contentDescription = "Delete"
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding()) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    checked = selectedMovies.contains(movie.imdbId),
                    onCheckedChange = { checked ->
                        selectedMovies =
                            if (checked)
                                selectedMovies + movie.imdbId
                            else
                                selectedMovies - movie.imdbId
                    }
                )
            }
        }
    }
}