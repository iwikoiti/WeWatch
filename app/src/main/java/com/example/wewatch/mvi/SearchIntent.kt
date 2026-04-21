package com.example.wewatch.mvi

sealed class SearchIntent {
    data class Search(val title: String, val year: String?) : SearchIntent()
}