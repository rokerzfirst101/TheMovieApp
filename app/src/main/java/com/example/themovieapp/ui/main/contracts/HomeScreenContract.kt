package com.example.themovieapp.ui.main.contracts

import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.main.viewmodel.ViewEvent
import com.example.themovieapp.ui.main.viewmodel.ViewSideEffect
import com.example.themovieapp.ui.main.viewmodel.ViewState

class HomeScreenContract {

    sealed class Event: ViewEvent {
        data class Search(val searchText: String) : Event()
        data class CategorySelection(val categoryName: String) : Event()
        data class MovieSelection(val movie: Movie) : Event()
        object GetNextPage: Event()
    }

    data class State(
        val selectedCategory: String = "popular",
        val paging: Int = 1,
        val movies: List<Movie> = listOf(),
        val isLoading: Boolean = true,
        val searchText: String = ""
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object ToastDataWasLoaded : Effect()

        sealed class Navigation : Effect() {
            data class ToMovieDetails(val movie: Movie): Navigation()
        }
    }
}