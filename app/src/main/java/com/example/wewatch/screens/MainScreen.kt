package com.example.wewatch.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.components.MovieItem

@Composable
fun MainScreen(
    movies: List<MovieEntity>,
    onAddClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет выбранных фильмов")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(movies) { movie ->

                    MovieItem(
                        movie = movie,
                        onCheckedChange = {}
                    )
                }
            }
        }
    }
}