package com.example.themovieapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovieapp.data.model.GetMoviesResponse
import com.example.themovieapp.data.repository.HomeRepository
import com.example.themovieapp.utils.NetworkHelper
import com.example.themovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<GetMoviesResponse>>()
    val movies: LiveData<Resource<GetMoviesResponse>>
        get() = _movies

    fun searchMovies(searchText: String) {
        fetchMoviesBySearch(searchText)
    }

    fun changeSelectedCategory(index: Int) {
        val category = when(index) {
            0 -> "popular"
            1 -> "latest"
            2 -> "now_playing"
            3 -> "top_rated"
            4 -> "upcoming"
            else -> "popular"
        }
        fetchMoviesByCategory(category)
    }

    init {
       fetchMoviesByCategory()
    }

    private fun fetchMoviesBySearch(searchText: String) {
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                homeRepository.getMovieBySearch(searchText).let {
                    if (it.isSuccessful) {
                        _movies.postValue(Resource.success(it.body()))
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }

    private fun fetchMoviesByCategory(category: String = "popular") {
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                homeRepository.getMoviesByCategory(category).let {
                    if (it.isSuccessful) {
                        _movies.postValue(Resource.success(it.body()))
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }
}