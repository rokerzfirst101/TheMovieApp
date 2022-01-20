package com.example.themovieapp.data.repository

import com.example.themovieapp.data.api.ApiHelper
import com.example.themovieapp.data.model.GetMoviesResponse
import com.example.themovieapp.utils.BaseApiResponse
import com.example.themovieapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiHelper: ApiHelper
) : BaseApiResponse() {

    suspend fun getMoviesByCategory(
        category: String,
        page: Int
    ) : Flow<NetworkResult<GetMoviesResponse>> {
        return flow<NetworkResult<GetMoviesResponse>> {
            emit(safeApiCall { apiHelper.getMoviesByCategory(category, page) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieBySearch(
        searchText: String
    ) : Flow<NetworkResult<GetMoviesResponse>> {
        return flow<NetworkResult<GetMoviesResponse>> {
            emit(safeApiCall { apiHelper.getMovieBySearch(searchText) })
        }.flowOn(Dispatchers.IO)
    }
}