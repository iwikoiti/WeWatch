package com.example.wewatch.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddScreen(
    onSearchClick: (String, String?) -> Unit,
    onAddClick: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Movie title") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSearchClick(title, year) }
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddClick
        ) {
            Text("Add movie")
        }
    }
}