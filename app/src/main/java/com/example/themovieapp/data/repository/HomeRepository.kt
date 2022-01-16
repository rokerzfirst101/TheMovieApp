package com.example.themovieapp.data.repository

import com.example.themovieapp.data.api.ApiHelper
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getMoviesByCategory(category: String) = apiHelper.getMoviesByCategory(category)
    suspend fun getMovieBySearch(searchText: String) = apiHelper.getMovieBySearch(searchText)
}