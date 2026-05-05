package com.example.wewatch.data.repository

import com.example.wewatch.data.api.ApiResult
import com.example.wewatch.data.database.MovieDao
import com.example.wewatch.data.database.MovieRepository as LegacyMovieRepository
import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
    private val remoteRepository: LegacyMovieRepository
) : MovieRepository {

    override suspend fun getMovies(): List<MovieEntity> {
        return movieDao.getAllMovies()
    }

    override suspend fun addMovie(movie: MovieEntity) {
        movieDao.insert(movie)
    }

    override suspend fun deleteMovies(movieIds: List<String>) {
        movieDao.deleteMoviesByIds(movieIds)
    }

    override suspend fun searchMovies(title: String, year: String?): Result<List<MovieEntity>> {
        return when (val result = remoteRepository.searchMovies(title, year)) {
            is ApiResult.Success -> {
                val movies = result.movies.filterNotNull()
                if (movies.isNotEmpty()) {
                    Result.success(movies)
                } else {
                    Result.failure(Exception("No movies found"))
                }
            }
            is ApiResult.Error -> {
                Result.failure(Exception(result.message))
            }
        }
    }
}