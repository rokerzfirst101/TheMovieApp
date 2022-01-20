package com.example.themovieapp.ui.main.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.main.contracts.HomeScreenContract
import com.example.themovieapp.ui.theme.TheMovieAppTheme
import com.example.themovieapp.ui.main.viewmodel.HomeViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            TheMovieAppTheme {
                NavigationComponent(navController = navController)
            }
        }
    }
}

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)

@ExperimentalAnimationApi
@Composable
fun NavigationComponent(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(
            "home",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
            }
        ) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val state = homeViewModel.viewState.value
            HomeScreen(
                state = state,
                effectFlow = homeViewModel.effect,
                onEventSent = { event -> homeViewModel.setEvent(event) },
                onNavigationRequested = { navigationEffect ->
                    if (navigationEffect is HomeScreenContract.Effect.Navigation.ToMovieDetails) {
                        navController.navigate("movieDetails/movie=${navigationEffect.movie}")
                    }
                })
        }
        composable("movieDetails/movie={movie}") {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val movieData = it.arguments?.getString("movie")
            val movie = Gson().fromJson(URLDecoder.decode(movieData, "utf-8"), Movie::class.java)
            MovieDetailScreen(movie, navController, homeViewModel)
        }
    }
}