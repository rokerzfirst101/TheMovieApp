package com.example.themovieapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    val adult: Boolean,
    @ColumnInfo(name = "genreIds")
    @SerializedName("genre_ids")
    val genreIds: List<String>,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String,
    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average")
    val voteAverage: String,
    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo(name = "posterPath")
    @SerializedName("poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdropPath")
    @SerializedName("backdrop_path")
    val backdropPath: String,
    var isLiked: Boolean
)