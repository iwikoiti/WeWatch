package com.example.wewatch.data.api

import retrofit2.http.GET
import retrofit2.http.Query

//интерфейс, который Retrofit использует для создания HTTP запросов к OMDB API
interface ApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") search: String,
        @Query("y") year: String? = null
    ): ApiResponse
}