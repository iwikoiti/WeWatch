package com.example.wewatch.presentation.mvi

import com.example.wewatch.domain.model.MovieEntity

sealed class MainIntent {
    object LoadMovies : MainIntent()
    data class DeleteMovies(val movies: List<MovieEntity>) : MainIntent()
}