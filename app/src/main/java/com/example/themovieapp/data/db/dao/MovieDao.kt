package com.example.themovieapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.themovieapp.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    suspend fun getFavourites(): List<Movie>
    
    @Insert
    suspend fun setFavourite(vararg movie: Movie)
    
    @Delete
    suspend fun removeFavourite(movie: Movie)

}