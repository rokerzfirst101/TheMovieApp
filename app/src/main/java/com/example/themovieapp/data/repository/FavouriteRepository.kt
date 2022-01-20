package com.example.themovieapp.data.repository

import com.example.themovieapp.data.db.dao.MovieDao
import com.example.themovieapp.data.model.Movie
import javax.inject.Inject

class FavouriteRepository @Inject constructor(private val movieDao: MovieDao) {
    suspend fun getFavourites(): List<Movie> = movieDao.getFavourites()
    suspend fun addToFavourite(movie: Movie): Unit = movieDao.setFavourite(movie)
    suspend fun removeFromFavourite(movie: Movie): Unit = movieDao.removeFavourite(movie)
}