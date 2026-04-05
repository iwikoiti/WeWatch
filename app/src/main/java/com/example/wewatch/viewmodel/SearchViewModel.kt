package com.example.wewatch.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.api.ApiResult
import com.example.wewatch.database.MovieRepository
import com.example.wewatch.model.MovieEntity
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    var searchResults by mutableStateOf<List<MovieEntity>>(emptyList())
        private set

    fun searchMovies(title: String, year: String?) {
        viewModelScope.launch {
            when (val result = repository.searchMovies(title, year)) {
                is ApiResult.Success -> {
                    searchResults = result.movies.filterNotNull()
                }
                is ApiResult.Error -> {
                    searchResults = emptyList()
                }
            }
        }
    }
}