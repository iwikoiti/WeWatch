package com.example.wewatch.mvi

import com.example.wewatch.model.MovieEntity

sealed class AddIntent {
    data class AddMovie(val movie: MovieEntity) : AddIntent()
}