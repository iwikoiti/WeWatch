package com.example.wewatch.api

import com.example.wewatch.model.MovieEntity

//Обработка ошибок
sealed class ApiResult {
    data class Success(val movies: List<MovieEntity?>) : ApiResult()
    data class Error(val message: String) : ApiResult()
}