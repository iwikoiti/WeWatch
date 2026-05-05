package com.example.wewatch.presentation.mvi

import com.example.wewatch.domain.model.MovieEntity

data class MainState(
    val movies: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false
)