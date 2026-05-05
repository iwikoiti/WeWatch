package com.example.wewatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wewatch.data.api.ApiClient
import com.example.wewatch.data.repository.MovieRepositoryImpl
import com.example.wewatch.data.database.MovieDatabase
import com.example.wewatch.data.repository.MovieRemoteDataSource
import com.example.wewatch.domain.repository.MovieRepository as DomainMovieRepository
import com.example.wewatch.domain.usecase.AddMovieUseCase
import com.example.wewatch.domain.usecase.DeleteMoviesUseCase
import com.example.wewatch.domain.usecase.GetMoviesUseCase
import com.example.wewatch.domain.usecase.SearchMoviesUseCase
import com.example.wewatch.presentation.screens.navigation.AppNavigation
import com.example.wewatch.ui.theme.WeWatchTheme
import com.example.wewatch.presentation.viewmodel.AddViewModel
import com.example.wewatch.presentation.viewmodel.MainViewModel
import com.example.wewatch.presentation.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeWatchTheme {
                WeWatchApp()
            }
        }
    }

    @Composable
    fun WeWatchApp() {
        // Dependency injection
        val database = MovieDatabase.getDatabase(this)
        val movieDao = database.movieDao()
        val remoteDataSource = MovieRemoteDataSource(ApiClient.apiService)
        val repository: DomainMovieRepository = MovieRepositoryImpl(movieDao, remoteDataSource)

        // Use Cases
        val getMoviesUseCase = GetMoviesUseCase(repository)
        val addMovieUseCase = AddMovieUseCase(repository)
        val deleteMoviesUseCase = DeleteMoviesUseCase(repository)
        val searchMoviesUseCase = SearchMoviesUseCase(repository)

        // ViewModels
        val mainViewModel: MainViewModel = viewModel {
            MainViewModel(getMoviesUseCase, deleteMoviesUseCase)
        }
        val searchViewModel: SearchViewModel = viewModel {
            SearchViewModel(searchMoviesUseCase)
        }
        val addViewModel: AddViewModel = viewModel {
            AddViewModel(addMovieUseCase)
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation(
                mainViewModel = mainViewModel,
                searchViewModel = searchViewModel,
                addViewModel = addViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}