package com.example.wewatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.database.MovieRepository

class MovieViewModelFactory(
    private val repository: MovieRepository,
    private val database: MovieDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(database) as T
            modelClass.isAssignableFrom(AddViewModel::class.java) ->
                AddViewModel(database) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}