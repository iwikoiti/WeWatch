package com.example.wewatch.presentation.mvi

sealed class SearchIntent {
    data class Search(val title: String, val year: String?) : SearchIntent()
}