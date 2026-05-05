package com.example.wewatch.presentation.mvi

import com.example.wewatch.domain.model.MovieEntity

sealed class AddIntent {
    data class AddMovie(val movie: MovieEntity) : AddIntent()
}