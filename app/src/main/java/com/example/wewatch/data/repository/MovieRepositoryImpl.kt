package com.example.wewatch.data.repository

import com.example.wewatch.data.database.MovieDao
import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
    private val remoteDataSource: MovieRemoteDataSource
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
        return remoteDataSource.searchMovies(title, year)
    }
}