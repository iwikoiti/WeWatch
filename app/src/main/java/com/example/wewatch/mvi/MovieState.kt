package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

data class MovieState(
    // Main Screen
    val movies: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,

    // Add Screen
    val title: String = "",
    val year: String = "",
    val foundMovie: MovieEntity? = null,
    val selectedMovieFromSearch: MovieEntity? = null,
    val isAddingMovie: Boolean = false,

    // Search Screen
    val searchResults: List<MovieEntity> = emptyList(),
    val isSearching: Boolean = false,
    val searchError: String? = null,
)