package com.example.wewatch.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.api.ApiResult
import com.example.wewatch.database.MovieRepository
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.mvi.SearchIntent
import com.example.wewatch.mvi.SearchState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MovieRepository
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

            when (val result = repository.searchMovies(title, year)) {
                is ApiResult.Success -> {
                    _state.value = _state.value.copy(
                        searchResults = result.movies.filterNotNull(),
                        isLoading = false
                    )
                }
                is ApiResult.Error -> {
                    _state.value = _state.value.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}