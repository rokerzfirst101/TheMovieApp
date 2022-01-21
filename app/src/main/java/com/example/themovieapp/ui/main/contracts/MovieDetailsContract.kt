package com.example.themovieapp.ui.main.contracts

import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.main.viewmodel.ViewEvent
import com.example.themovieapp.ui.main.viewmodel.ViewSideEffect
import com.example.themovieapp.ui.main.viewmodel.ViewState

class MovieDetailsContract {

    sealed class Event: ViewEvent {
        object FavouriteOnClick : Event()
        object GoBack : Event()
    }

    data class State(
        val movie: Movie? = null,
        val isLiked: Boolean = false
    ) : ViewState

    sealed class Effect: ViewSideEffect {
        sealed class Navigation : Effect() {
            object goBack: Navigation()
        }
    }

}