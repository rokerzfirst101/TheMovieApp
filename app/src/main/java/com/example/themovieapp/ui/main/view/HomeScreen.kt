package com.example.themovieapp.ui.main.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.themovieapp.R
import com.example.themovieapp.ui.main.contracts.HomeScreenContract
import com.example.themovieapp.ui.theme.*
import com.example.themovieapp.ui.main.view.components.HomeMovieListItem
import com.example.themovieapp.ui.main.viewmodel.LAUNCH_LISTEN_FOR_EFFECTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    state: HomeScreenContract.State,
    effectFlow: Flow<HomeScreenContract.Effect>?,
    onEventSent: (event: HomeScreenContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: HomeScreenContract.Effect.Navigation) -> Unit
) {

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is HomeScreenContract.Effect.ToastDataWasLoaded -> {
                    Log.d(TAG, "HomeScreen: Data Loaded!") }
                is HomeScreenContract.Effect.Navigation.ToMovieDetails -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }

    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize()
    ) {
        Column {
            HeaderSection("Rakshit")
            SearchBar(state.searchText, onEventSent)
            if (state.searchText == "") {
                CategorySection(state, onEventSent, listOf(
                    "popular",
                    "now_playing",
                    "top_rated",
                    "upcoming",
                    "favourites"
                ))
            } else {
                SearchSection(state.searchText)
            }
            LazyColumn {
                itemsIndexed(state.movies) { index, item ->
                    if (index == state.movies.lastIndex) {
                        onEventSent(HomeScreenContract.Event.GetNextPage)
                    }
                    HomeMovieListItem(
                        movie = item,
                        modifier = Modifier.clickable {
                            onEventSent(HomeScreenContract.Event.MovieSelection(item))
                        }
                    )
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
fun CategorySection(state: HomeScreenContract.State, onEventSent: (event: HomeScreenContract.Event) -> Unit, chips: List<String>) {
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
                            onEventSent(HomeScreenContract.Event.CategorySelection(chips[it]))
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (state.selectedCategory == chips[it]) ActiveButtonColor
                            else SurfaceColor
                        )
                        .padding(15.dp)
                ) {
                    Text(
                        text = formatString(chips[it]),
                        color = HTextColor
                    )
                }
            }
        }
    }
}

fun formatString(s: String): String {
    val words = s.split("_")
    var newStr = ""
    words.forEach { word ->
        newStr += word.replaceFirstChar {
            it.uppercase()
        } + " "
    }
    return newStr.trimEnd()
}

@Composable
fun SearchBar(searchText: String, onEventSent: (event: HomeScreenContract.Event) -> Unit,) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = SurfaceColor,
                cursorColor = HTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                onEventSent(HomeScreenContract.Event.Search(it))
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
