package com.example.wewatch.domain.usecase

import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<MovieEntity> = repository.getMovies()
}