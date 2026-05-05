package com.example.wewatch.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.usecase.DeleteMoviesUseCase
import com.example.wewatch.domain.usecase.GetMoviesUseCase
import com.example.wewatch.presentation.mvi.MainIntent
import com.example.wewatch.presentation.mvi.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val deleteMoviesUseCase: DeleteMoviesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: MainState by _state

    private val intents = Channel<MainIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
        sendIntent(MainIntent.LoadMovies)
    }

    fun sendIntent(intent: MainIntent) {
        intents.trySend(intent)
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeEach { intent ->
                when (intent) {
                    is MainIntent.LoadMovies -> loadMovies()
                    is MainIntent.DeleteMovies -> deleteMovies(intent.movies)
                }
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val movies = getMoviesUseCase()
            _state.value = _state.value.copy(movies = movies, isLoading = false)
        }
    }

    private fun deleteMovies(moviesToDelete: List<MovieEntity>) {
        viewModelScope.launch {
            deleteMoviesUseCase(moviesToDelete)
            loadMovies()
        }
    }
}