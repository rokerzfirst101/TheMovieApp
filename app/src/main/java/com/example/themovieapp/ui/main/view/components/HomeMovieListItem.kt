package com.example.themovieapp.ui.main.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.themovieapp.R
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.theme.GoldColor

const val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

@Composable
fun HomeMovieListItem(movie: Movie, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(
                data = "$imageBaseUrl${movie.posterPath}",
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Action, Adventure, Drama",
                style = MaterialTheme.typography.body1,
            )
            Row {
                Chip(text = movie.releaseDate.split("-")[0])
                Chip(text = "13+")
            }
            Row {
                Chip(text = movie.voteAverage, icon = R.drawable.ic_star, color = GoldColor)
            }
        }
    }
}