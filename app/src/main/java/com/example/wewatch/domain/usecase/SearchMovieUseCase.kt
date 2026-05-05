package com.example.wewatch.domain.usecase

import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class SearchMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(title: String, year: String?): Result<List<MovieEntity>> {
        return repository.searchMovies(title, year)
    }
}