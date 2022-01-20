package com.example.themovieapp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.db.AppDatabase
import com.example.themovieapp.data.model.GetMoviesResponse
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.data.repository.FavouriteRepository
import com.example.themovieapp.data.repository.HomeRepository
import com.example.themovieapp.ui.main.contracts.HomeScreenContract
import com.example.themovieapp.utils.NetworkHelper
import com.example.themovieapp.utils.NetworkResult
import com.example.themovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val favouriteRepository: FavouriteRepository,
    private val networkHelper: NetworkHelper
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
                fetchMovies()
            }
            is HomeScreenContract.Event.GetNextPage -> {
                setState {
                    copy(paging = viewState.value.paging+1)
                }
                fetchMovies()
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