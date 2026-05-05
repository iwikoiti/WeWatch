package com.example.wewatch.data.repository

import com.example.wewatch.data.api.ApiService
import com.example.wewatch.domain.model.MovieEntity

class MovieRemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun searchMovies(searchQuery: String, year: String? = null): Result<List<MovieEntity>> {
        return try {
            val response = apiService.searchMovies(searchQuery, year)

            if (response.response == "True") {
                val movies = response.movies?.mapNotNull { movieResponse ->
                    movieResponse.toMovie()
                } ?: emptyList()

                if (movies.isNotEmpty()) {
                    Result.success(movies)
                } else {
                    Result.failure(Exception("No movies found"))
                }
            } else {
                Result.failure(Exception(response.error ?: "Unknown API error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}