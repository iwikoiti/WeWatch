package com.example.wewatch.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wewatch.domain.model.MovieEntity
import com.example.wewatch.domain.usecase.AddMovieUseCase
import com.example.wewatch.presentation.mvi.AddIntent
import com.example.wewatch.presentation.mvi.AddState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class AddViewModel(
    private val addMovieUseCase: AddMovieUseCase
) : ViewModel() {

    private val state = mutableStateOf(AddState())

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
            state.value = state.value.copy(isLoading = true)
            addMovieUseCase(movie)
            state.value = state.value.copy(isAdded = true, isLoading = false)
        }
    }
}