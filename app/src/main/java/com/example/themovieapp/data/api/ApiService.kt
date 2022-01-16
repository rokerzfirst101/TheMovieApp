package com.example.themovieapp.data.api

import com.example.themovieapp.data.model.GetMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{category}?api_key=65db5aebb7dc29d77c7b00443904e829&language=en-US")
    suspend fun getMoviesByCategory(
        @Path("category")
        category: String
    ): Response<GetMoviesResponse>

    @GET("search/movie?api_key=65db5aebb7dc29d77c7b00443904e829&language=en-US&page=1&include_adult=true")
    suspend fun getMoviesBySearch(
        @Query("query")
        searchVal: String
    ): Response<GetMoviesResponse>

}