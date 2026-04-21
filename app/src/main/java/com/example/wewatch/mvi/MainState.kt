package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

data class MainState(
    val movies: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false
)