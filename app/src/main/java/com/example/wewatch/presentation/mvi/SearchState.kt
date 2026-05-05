package com.example.wewatch.presentation.mvi

import com.example.wewatch.domain.model.MovieEntity

data class SearchState(
    val searchResults: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)