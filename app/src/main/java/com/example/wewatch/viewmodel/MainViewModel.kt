package com.example.wewatch.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.model.MovieEntity
import kotlinx.coroutines.launch

class MainViewModel (
    private val database: MovieDatabase,
): ViewModel() {

    var movies by mutableStateOf<List<MovieEntity>>(emptyList())
        private set

    init {
        loadMoviesFromDb()
    }

    fun loadMoviesFromDb() {
        viewModelScope.launch {
            movies = database.movieDao().getAllMovies()
        }
    }

    fun deleteMovies(moviesToDelete: List<MovieEntity>) {
        viewModelScope.launch {
            val ids = moviesToDelete.map { it.imdbId }
            database.movieDao().deleteMoviesByIds(ids)
            loadMoviesFromDb()
        }
    }
}