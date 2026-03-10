package com.example.wewatch.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.wewatch.model.MovieEntity

@Composable
fun MovieItem(
    movie: MovieEntity,
    onCheckedChange: (Boolean) -> Unit
) {

    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        AsyncImage(
            model = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {

            Text(movie.title)

            Text(movie.year)
        }

        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(it)
            }
        )
    }
}