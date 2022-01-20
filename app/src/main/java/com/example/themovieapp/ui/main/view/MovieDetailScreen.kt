package com.example.themovieapp.ui.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.ui.theme.HTextColor
import com.example.themovieapp.R
import com.example.themovieapp.ui.main.viewmodel.HomeViewModel
import com.example.themovieapp.ui.theme.BackgroundColor
import com.example.themovieapp.ui.theme.HeartRedColor
import com.example.themovieapp.ui.theme.SurfaceColor

const val baseUrl = "https://image.tmdb.org/t/p/w1280"

@Composable
fun MovieDetailScreen(movie: Movie, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        HeaderSection(
            navController,
            movie
        ) {
//            if (it) {
//                homeViewModel.removeFromLiked(movie)
//            } else {
//                homeViewModel.addToLiked(movie)
//            }
        }
        MovieDescription(movie.overview)
    }
}

@Composable
fun MovieDescription(overview: String) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Overview", style = MaterialTheme.typography.h4)
        Text(text = overview, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun HeaderSection(navController: NavHostController, movie: Movie, block: (isFavourite: Boolean) -> Unit) {
    var isFavourite by remember {
        mutableStateOf(movie.isLiked)
    }
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
                data = "$baseUrl${movie.backdropPath}",
                builder = {
                    scale(Scale.FILL)
                }
            ),
            contentDescription = "backdrop",
            contentScale = ContentScale.FillHeight
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        SurfaceColor,
                    )
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SurfaceColor.copy(alpha = 0.6f))
                        .padding(5.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "backButton",
                        modifier = Modifier
                            .size(30.dp),
                        tint = HTextColor
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SurfaceColor.copy(alpha = 0.6f))
                        .padding(5.dp)
                        .clickable {
                            block(isFavourite)
                            isFavourite = !isFavourite
                        }
                ) {
                    if (isFavourite) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "backButton",
                            modifier = Modifier
                                .size(24.dp),
                            tint = HeartRedColor
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart_outline),
                            contentDescription = "backButton",
                            modifier = Modifier
                                .size(24.dp),
                            tint = HTextColor
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(SurfaceColor.copy(alpha = 0.6f))
                    .padding(15.dp)
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.h1)
            }
        }
    }
}