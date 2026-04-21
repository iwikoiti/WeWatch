package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

sealed class MainIntent {
    object LoadMovies : MainIntent()
    data class DeleteMovies(val movies: List<MovieEntity>) : MainIntent()
}