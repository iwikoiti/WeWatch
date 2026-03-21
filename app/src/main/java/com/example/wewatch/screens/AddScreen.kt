package com.example.wewatch.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.wewatch.model.MovieEntity

@Composable
fun AddScreen(
    selectedMovie: MovieEntity?,
    onSearchAll: (String, String?) -> Unit,
    onSearchSingle: (String, String?, (MovieEntity?) -> Unit) -> Unit,
    onAddMovie: (MovieEntity) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var foundMovie by remember { mutableStateOf<MovieEntity?>(null) }
    val displayMovie = selectedMovie ?: foundMovie
    if (displayMovie != null && title != displayMovie.title) {
        title = displayMovie.title
        year = displayMovie.year
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                foundMovie = null },
            label = { Text("Movie title") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSearching
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = year,
            onValueChange = {
                year = it
                foundMovie = null
            },
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSearching
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    onSearchAll(title, year)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSearching
        ) {
            Text("Search all")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {
                    isSearching = true
                    onSearchSingle(title, year) { movie ->
                        foundMovie = movie
                        isSearching = false

                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSearching
        ) {
            Text(if (isSearching) "Searching..." else "Search single")
        }

        // Постер найденного фильма
        if (displayMovie != null && displayMovie.poster.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = displayMovie.poster,
                contentDescription = displayMovie.title,
                modifier = Modifier
                    .size(200.dp, 300.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка добавления
            Button(
                onClick = { onAddMovie(displayMovie) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add movie")
            }
        }
    }
}