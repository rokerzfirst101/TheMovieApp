package com.example.themovieapp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.data.repository.MovieDetailsRepository
import com.example.themovieapp.ui.main.contracts.MovieDetailsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "MovieDetailsView"

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) : BaseViewModel<
        MovieDetailsContract.Event,
        MovieDetailsContract.State,
        MovieDetailsContract.Effect>() {

    fun setMovie(movie: Movie) {
        setState {
            copy(movie = movie)
        }
        viewModelScope.launch {
            viewState.value.movie?.let {
                movieDetailsRepository.isMovieLiked(it)
                    .collect { value ->
                        Log.d(TAG, "setMovie: $value")
                        setState {
                            copy(isLiked = value)
                        }
                    }
            }
        }
    }

    override fun setInitialState(): MovieDetailsContract.State =
        MovieDetailsContract.State()

    override fun handleEvents(event: MovieDetailsContract.Event) {
        when (event) {
            is MovieDetailsContract.Event.FavouriteOnClick -> {
                toggleFavourite()
            }
            is MovieDetailsContract.Event.GoBack -> {
                setEffect {
                    MovieDetailsContract.Effect.Navigation.goBack
                }
            }
        }
    }

    private fun toggleFavourite() {
        viewModelScope.launch {
            viewState.value.movie?.let {
                if (viewState.value.isLiked) movieDetailsRepository.removeFromFavourite(it)
                else movieDetailsRepository.addToFavourites(it)
            }
            setState {
                copy(isLiked = !isLiked)
            }
        }
    }
}