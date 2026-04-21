package com.example.wewatch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.mvi.AddIntent
import com.example.wewatch.mvi.AddState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class AddViewModel(
    private val database: MovieDatabase
) : ViewModel() {

    private val _state = mutableStateOf(AddState())
    val state: AddState by _state

    private val intents = Channel<AddIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    fun sendIntent(intent: AddIntent) {
        intents.trySend(intent)
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intents.consumeEach { intent ->
                when (intent) {
                    is AddIntent.AddMovie -> addMovie(intent.movie)
                }
            }
        }
    }

    private fun addMovie(movie: MovieEntity) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            database.movieDao().insert(movie)
            _state.value = _state.value.copy(isAdded = true, isLoading = false)
        }
    }
}