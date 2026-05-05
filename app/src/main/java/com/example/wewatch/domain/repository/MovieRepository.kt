package com.example.wewatch.domain.repository

import com.example.wewatch.domain.model.MovieEntity

interface MovieRepository {
    suspend fun getMovies(): List<MovieEntity>
    suspend fun addMovie(movie: MovieEntity)
    suspend fun deleteMovies(movieIds: List<String>)
    suspend fun searchMovies(title: String, year: String?): Result<List<MovieEntity>>
}