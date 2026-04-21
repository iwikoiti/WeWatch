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
import com.example.wewatch.api.ApiClient
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.database.MovieRepository
import com.example.wewatch.screens.navigation.AppNavigation
import com.example.wewatch.ui.theme.WeWatchTheme
import com.example.wewatch.viewmodel.AddViewModel
import com.example.wewatch.viewmodel.MainViewModel
import com.example.wewatch.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {
    private lateinit var db: MovieDatabase
    private lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = MovieDatabase.getDatabase(this)
        repository = MovieRepository(ApiClient.apiService)

        setContent {
            WeWatchTheme {
                WeWatchApp(db, repository)
            }
        }
    }

    @Composable
    fun WeWatchApp(
        db: MovieDatabase,
        repository: MovieRepository
    ){
        val mainViewModel: MainViewModel = viewModel { MainViewModel(db) }
        val searchViewModel: SearchViewModel = viewModel { SearchViewModel(repository) }
        val addViewModel: AddViewModel = viewModel { AddViewModel(db) }

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
