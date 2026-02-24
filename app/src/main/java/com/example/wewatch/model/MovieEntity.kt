package com.example.wewatch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

class MovieEntity {

    @Entity(tableName = "movies")
    data class MovieEntity(
        @PrimaryKey(autoGenerate = true)
        val imdbID: String,
        val title: String,
        val year: String,
        val poster: String,
        val genre: String? = null,
        val isChecked: Boolean = false
    )
}