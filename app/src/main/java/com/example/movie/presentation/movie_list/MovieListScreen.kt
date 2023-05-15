package com.example.movie.presentation.movie_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movie.Screen
import com.example.movie.common.Constants.IMAGE_BASE_URL
import com.example.movie.domain.model.MovieDetail

@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            SearchView(
                viewModel = viewModel,
            )
            MovieList(
                navController = navController,
                state = state,
            )
        }


        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .align(alignment = Alignment.Center),
                color = MaterialTheme.colorScheme.error
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun MovieList(
    navController: NavController,
    state: MovieListState,
) {
    state.movies?.results?.let { movieList ->
        if(movieList.isEmpty()){
            Text(
                text = "No movies found",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }else{
            LazyVerticalGrid(
                columns = GridCells.Adaptive(110.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movieList.size) { index ->
                    MovieListItem(
                        movie = movieList[index],
                        navController = navController,
                    )
                }
            }
        }
    } ?: Text(
        text = "No movies found",
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge
    )
}



@Composable
fun MovieListItem(
    movie: MovieDetail,
    navController: NavController,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
            },
        shape = RoundedCornerShape(0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(0.7f)
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = IMAGE_BASE_URL + movie.posterPath,
                    )
                    .apply(block = fun ImageRequest.Builder.() {
                        memoryCachePolicy(CachePolicy.ENABLED)
                    }).build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> { }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
fun SearchView(
    viewModel: MovieListViewModel
) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Spacer(
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .windowInsetsTopHeight(WindowInsets.statusBars)
    )

    TextField(
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                viewModel.getMovies(query = searchQueryState.value.text)
                keyboardController?.hide()
            }
        ),
        value = searchQueryState.value,
        onValueChange = { value ->
            searchQueryState.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            IconButton(onClick = {
                viewModel.getMovies(query = searchQueryState.value.text)
                keyboardController?.hide()
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (searchQueryState.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        searchQueryState.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "Search for a movie",
                color = Color.White
            )
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedLeadingIconColor =  Color.White,
            unfocusedLeadingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White,
            containerColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}