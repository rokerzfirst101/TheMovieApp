package com.example.themovieapp.data.db

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.themovieapp.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieTypeConverter {

    @TypeConverter
    fun listToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(string: String): List<String> {
        Log.d("GSON", "stringToList: $string")
        val itemType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, itemType)
    }

}