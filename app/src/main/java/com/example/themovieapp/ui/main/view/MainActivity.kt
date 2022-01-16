package com.example.themovieapp.ui.main.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.theme.TheMovieAppTheme
import com.example.themovieapp.ui.main.viewmodel.HomeViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TheMovieAppTheme {
                NavigationComponent(navController = navController)
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(homeViewModel, navController)
        }
        composable("movieDetails/movie={movie}") {
            val movieData = it.arguments?.getString("movie")
            val movie = Gson().fromJson(URLDecoder.decode(movieData, "utf-8"), Movie::class.java)
            MovieDetailScreen(movie)
        }
    }
}