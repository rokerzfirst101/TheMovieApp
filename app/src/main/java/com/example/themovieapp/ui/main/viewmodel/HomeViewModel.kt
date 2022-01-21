package com.example.themovieapp.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.data.repository.FavouriteRepository
import com.example.themovieapp.data.repository.HomeRepository
import com.example.themovieapp.ui.main.contracts.HomeScreenContract
import com.example.themovieapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : BaseViewModel<
        HomeScreenContract.Event,
        HomeScreenContract.State,
        HomeScreenContract.Effect>() {

    init {
        fetchMovies()
    }

    override fun setInitialState(): HomeScreenContract.State =
        HomeScreenContract.State()

    override fun handleEvents(event: HomeScreenContract.Event) {
        when (event) {
            is HomeScreenContract.Event.Search -> {
                setState {
                    copy(movies = emptyList(), searchText = event.searchText)
                }
                if (event.searchText != "") searchMovies(event.searchText)
                else fetchMovies()
            }
            is HomeScreenContract.Event.MovieSelection -> {
                setEffect {
                    HomeScreenContract.Effect.Navigation.ToMovieDetails(event.movie)
                }
            }
            is HomeScreenContract.Event.CategorySelection -> {
                setState {
                    copy(movies = emptyList(), selectedCategory = event.categoryName, paging = 1)
                }
                if (event.categoryName == "favourites") fetchLocalMovies()
                else fetchMovies()
            }
            is HomeScreenContract.Event.GetNextPage -> {
                setState {
                    copy(paging = viewState.value.paging+1)
                }
                fetchMovies()
            }
        }
    }

    private fun fetchLocalMovies() = viewModelScope.launch {
        homeRepository.getFavouriteMovies()
            .collect { movies ->
                setState {
                    copy(movies = movies)
                }
            }
    }

    private fun searchMovies(searchText: String) = viewModelScope.launch {
        homeRepository.getMovieBySearch(searchText)
            .collect { values ->
                setState {
                    copy(movies = values.data?.results ?: emptyList())
                }
            }
    }

    private fun fetchMovies() = viewModelScope.launch {
        homeRepository.getMoviesByCategory(
            viewState.value.selectedCategory,
            viewState.value.paging
        ).collect { values ->
            if (viewState.value.selectedCategory != "favourites") {
                if (viewState.value.paging == 1) setState {
                    copy(movies = values.data?.results ?: emptyList())
                } else {
                    val mutableList = mutableListOf<Movie>()
                    mutableList.addAll(viewState.value.movies)
                    mutableList.addAll(values.data?.results ?: emptyList())
                    setState {
                        copy(movies = mutableList)
                    }
                }
            }
        }
    }
}