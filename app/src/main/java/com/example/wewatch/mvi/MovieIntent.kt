package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

sealed class MovieIntent {
    // Main Screen
    object LoadMovies : MovieIntent()
    data class DeleteMovies(val movies: List<MovieEntity>) : MovieIntent()

    // Search Screen
    data class SearchMovies(val title: String, val year: String?) : MovieIntent()
    data class ClearSearchResults(val title: String, val year: String?) : MovieIntent()

    // Add Screen
    data class AddMovie(val movie: MovieEntity) : MovieIntent()
    data class UpdateTitle(val title: String) : MovieIntent()
    data class UpdateYear(val year: String) : MovieIntent()
    data class SearchFromAddScreen(val title: String, val year: String?) : MovieIntent()

    // Navigation
    data class SelectMovieFromSearch(val movie: MovieEntity) : MovieIntent()
    object ClearSelectedMovie : MovieIntent()
    object NavigateBack : MovieIntent()
    object NavigateToAdd : MovieIntent()
    data class NavigateToSearch(val title: String, val year: String?) : MovieIntent()
}