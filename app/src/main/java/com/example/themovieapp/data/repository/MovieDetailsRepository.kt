package com.example.themovieapp.data.repository

import com.example.themovieapp.data.db.dao.MovieDao
import com.example.themovieapp.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    suspend fun getFavourites(): Flow<List<Movie>> {
        return flow {
            emit(movieDao.getFavourites())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun isMovieLiked(movie: Movie): Flow<Boolean> {
        return flow {
            emit(movieDao.isMovieLiked(movie.id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addToFavourites(movie: Movie) = movieDao.setFavourite(movie)

    suspend fun removeFromFavourite(movie: Movie): Unit = movieDao.removeFavourite(movie)
}