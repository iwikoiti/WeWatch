package com.example.wewatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.wewatch.api.ApiClient
import com.example.wewatch.api.ApiResult
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.database.MovieRepository
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.screens.navigation.AppNavigation
import com.example.wewatch.ui.theme.WeWatchTheme
import kotlinx.coroutines.launch

//Controller
class MainActivity : ComponentActivity() {
    private lateinit var db: MovieDatabase
    private lateinit var repository: MovieRepository

    // Состояние Activity/Controller
    private var movies by mutableStateOf<List<MovieEntity>>(emptyList())
    private var searchResults by mutableStateOf<List<MovieEntity>>(emptyList())
    private var selectedMovie by mutableStateOf<MovieEntity?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        db = MovieDatabase.getDatabase(this)
        repository = MovieRepository(ApiClient.apiService)
        loadMoviesFromDb()
        setContent {
            WeWatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        movies = movies,
                        searchResults = searchResults,
                        onAddMovie = { addMovie(it) },
                        onDeleteMovie = { movies ->
                            movies.forEach { deleteMovie(it) }
                        },
                        onSearchMovies = { title, year -> searchMovies(title, year) },
                        onSearchSingleMovie = { title, year, onResult ->
                            onSearchSingleMovie(title, year, onResult) }
                    )
                }
            }
        }
    }

    //Загрузка фильмов
    private fun loadMoviesFromDb() {
        lifecycleScope.launch {
            movies = db.movieDao().getAllMovies()
        }
    }

    //Поиск фильмов
    private fun searchMovies(title: String, year: String?) {
        lifecycleScope.launch {
            when(val result = repository.searchMovies(title, year)) {
                is com.example.wewatch.api.ApiResult.Success -> {
                    searchResults = result.movies.filterNotNull()
                }
                is com.example.wewatch.api.ApiResult.Error -> {
                    searchResults = emptyList()
                }
            }
        }
    }

    private fun onSearchSingleMovie(title: String, year: String?, onResult: (MovieEntity?) -> Unit) {
        lifecycleScope.launch {
            when (val result = repository.getMovie(title, year)) {
                is ApiResult.Success -> {
                    onResult(result.movies.firstOrNull())
                }
                is ApiResult.Error -> {
                    onResult(null)
                }
            }
        }
    }

    //Добавление фильма
    private fun addMovie(movie: MovieEntity) {
        lifecycleScope.launch {
            db.movieDao().insert(movie)
            loadMoviesFromDb()
        }
    }

    //Удаление фильма
    private fun deleteMovie(movie: MovieEntity) {
        lifecycleScope.launch {
            db.movieDao().delete(movie)
            loadMoviesFromDb()
        }
    }
}
