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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.wewatch.database.MovieDatabase
import com.example.wewatch.model.MovieEntity
import com.example.wewatch.ui.theme.WeWatchTheme
import kotlinx.coroutines.launch

//Controller
class MainActivity : ComponentActivity() {
    private lateinit var db: MovieDatabase
    private var movies: List<MovieEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        db = MovieDatabase.getDatabase(this)
        loadMovies()
        setContent {
            WeWatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        movies = movies,
                        onAddClick = { },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            movies = db.movieDao().getAllMovies()
        }

    }
}

@Composable
fun MainScreen(
    movies: List<MovieEntity>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
){

}
