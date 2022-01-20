package com.example.themovieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themovieapp.data.db.dao.MovieDao
import com.example.themovieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}