package com.example.themovieapp.ui.main.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.themovieapp.R
import com.example.themovieapp.ui.theme.*
import com.example.themovieapp.ui.main.view.components.HomeMovieListItem
import com.example.themovieapp.ui.main.viewmodel.HomeViewModel
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavHostController) {

    val data by homeViewModel.movies.observeAsState()
    val movieList = data?.data?.results ?: emptyList()

    val searchValue = remember {
        mutableStateOf(TextFieldValue())
    }

    TheMovieAppTheme {
        Box(
            modifier = Modifier
                .background(BackgroundColor)
                .fillMaxSize()
        ) {
            Column {
                HeaderSection("Rakshit")
                SearchBar(searchValue, homeViewModel)
                if (searchValue.value.text == "") {
                    CategorySection(homeViewModel, listOf(
                        "Popular",
                        "Latest",
                        "Now Playing",
                        "Top Rated",
                        "Upcoming"
                    ))
                } else {
                    SearchSection(searchValue.value.text)
                }
                LazyColumn {
                    items(movieList.size) {
                        HomeMovieListItem(
                            movie = movieList[it],
                            modifier = Modifier.clickable {
                                val string = Gson().toJson(movieList[it])
                                val newString = URLEncoder.encode(string, "utf-8")
                                navController.navigate("movieDetails/movie=$newString")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSection(searchText: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = "You searched for \"$searchText\"",
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun CategorySection(homeViewModel: HomeViewModel, chips: List<String>) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = "Categories",
            style = MaterialTheme.typography.h2
        )
        LazyRow(
            modifier = Modifier.padding(7.5.dp)
        ) {
            items(chips.size) {
                Box(
                    modifier = Modifier
                        .padding(start = 7.5.dp, end = 7.5.dp)
                        .clickable {
                            selectedChipIndex = it
                            homeViewModel.changeSelectedCategory(it)
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (selectedChipIndex == it) ActiveButtonColor
                            else SurfaceColor
                        )
                        .padding(15.dp)
                ) {
                    Text(
                        text = chips[it],
                        color = HTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchText: MutableState<TextFieldValue>, homeViewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText.value,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = SurfaceColor,
                cursorColor = HTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                searchText.value = it
                homeViewModel.searchMovies(it.text)
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text(text = "Search")},
            leadingIcon = { Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = BTextColor
            ) },
            singleLine = true,
            textStyle = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun HeaderSection(name: String) {
    Box(
        modifier = Modifier.padding(start = 25.dp, top = 25.dp)
    ) {
        Text(
            text = "Hi, $name!",
            style = MaterialTheme.typography.h1,
        )
    }
}
