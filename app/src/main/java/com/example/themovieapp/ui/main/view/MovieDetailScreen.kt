package com.example.themovieapp.ui.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.theme.HTextColor
import java.nio.file.Path

const val baseUrl = "https://image.tmdb.org/t/p/w1280"

@Composable
fun MovieDetailScreen(movie: Movie) {
    Column {
        HeaderSection(movie.title, movie.backdropPath)
        MovieDescription(movie.overview)
    }
}

@Composable
fun MovieDescription(overview: String) {
    Text(text = overview, style = MaterialTheme.typography.body1)
}

@Composable
fun HeaderSection(title: String, backdropPath: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .background(HTextColor),
            painter = rememberImagePainter(
                data = "$baseUrl$backdropPath",
                builder = {
                    scale(Scale.FILL)
                }
            ),
            contentDescription = "backdrop",
            contentScale = ContentScale.FillHeight
        )
        Text(text = title, style = MaterialTheme.typography.h1)
    }
}