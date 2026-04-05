package com.example.wewatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.model.MovieEntity
import kotlinx.coroutines.launch

class AddViewModel(
    private val database: MovieDatabase
) : ViewModel() {

    fun addMovie(movie: MovieEntity) {
        viewModelScope.launch {
            database.movieDao().insert(movie)
        }
    }
}