package com.example.themovieapp.data.api

import com.example.themovieapp.data.model.GetMoviesResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {
    override suspend fun getMoviesByCategory(category: String)
        : Response<GetMoviesResponse> = apiService.getMoviesByCategory(category)

    override suspend fun getMovieBySearch(searchText: String)
        : Response<GetMoviesResponse> = apiService.getMoviesBySearch(searchText)
}