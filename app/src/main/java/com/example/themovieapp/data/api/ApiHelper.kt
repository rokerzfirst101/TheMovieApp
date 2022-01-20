package com.example.themovieapp.data.api

import com.example.themovieapp.data.model.GetMoviesResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getMoviesByCategory(category: String, page: Int): Response<GetMoviesResponse>
    suspend fun getMovieBySearch(searchText: String): Response<GetMoviesResponse>
}