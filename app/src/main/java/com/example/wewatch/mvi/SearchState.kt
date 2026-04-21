package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

data class SearchState(
    val searchResults: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)