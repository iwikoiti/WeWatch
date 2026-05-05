package com.example.wewatch.domain.usecase

import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class AddMovieUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieEntity) = repository.addMovie(movie)
}