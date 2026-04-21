package com.example.wewatch.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.mvi.MainIntent
import com.example.wewatch.mvi.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val database: MovieDatabase
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
            val movies = database.movieDao().getAllMovies()
            _state.value = _state.value.copy(movies = movies, isLoading = false)
        }
    }

    private fun deleteMovies(moviesToDelete: List<MovieEntity>) {
        viewModelScope.launch {
            val ids = moviesToDelete.map { it.imdbId }
            database.movieDao().deleteMoviesByIds(ids)
            sendIntent(MainIntent.LoadMovies)
        }
    }
}