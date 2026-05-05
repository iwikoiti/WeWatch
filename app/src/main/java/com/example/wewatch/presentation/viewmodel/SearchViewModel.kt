package com.example.wewatch.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.domain.usecase.SearchMoviesUseCase
import com.example.wewatch.presentation.mvi.SearchIntent
import com.example.wewatch.presentation.mvi.SearchState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: SearchState by _state

    private val intents = Channel<SearchIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    fun sendIntent(intent: SearchIntent) {
        intents.trySend(intent)
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeEach { intent ->
                when (intent) {
                    is SearchIntent.Search -> searchMovies(intent.title, intent.year)
                }
            }
        }
    }

    private fun searchMovies(title: String, year: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = searchMoviesUseCase(title, year)

            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    searchResults = result.getOrNull() ?: emptyList(),
                    isLoading = false
                )
            } else {
                _state.value = _state.value.copy(
                    searchResults = emptyList(),
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Unknown error"
                )
            }
        }
    }
}