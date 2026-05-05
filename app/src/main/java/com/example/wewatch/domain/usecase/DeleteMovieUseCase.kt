package com.example.wewatch.domain.usecase

import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class DeleteMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movies: List<MovieEntity>) {
        val ids = movies.map { it.imdbId }
        repository.deleteMovies(ids)
    }
}