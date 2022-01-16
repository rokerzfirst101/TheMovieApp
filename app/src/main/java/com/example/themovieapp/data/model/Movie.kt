package com.example.themovieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("genre_ids")
    val genreIds: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
)