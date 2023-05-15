package com.example.movie.presentation.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movie.common.Constants.IMAGE_BASE_URL

@Composable
fun MovieDetail(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        state.movie?.let { movie ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp),
            ){
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        val backdropPath = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(
                                    data = IMAGE_BASE_URL + movie.backdropPath
                                )
                                .apply(block = fun ImageRequest.Builder.() {
                                    memoryCachePolicy(CachePolicy.ENABLED)
                                }).build()
                        )

                        Image(
                            painter = backdropPath,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )

                        Spacer(
                            Modifier
                                .background(Color(0x80000000))
                                .fillMaxWidth()
                                .windowInsetsTopHeight(WindowInsets.statusBars)
                        )
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            val posterPath = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        data = IMAGE_BASE_URL + movie.posterPath
                                    )
                                    .apply(block = fun ImageRequest.Builder.() {
                                        memoryCachePolicy(CachePolicy.ENABLED)
                                    }).build()
                            )
                            Image(
                                contentScale = ContentScale.Crop,
                                painter = posterPath,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(230.dp)
                                    .width(150.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                            ) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )

                                Row(
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star icon",
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(28.dp)
                                    )
                                    Text(
                                        text = movie.voteAverage.toString().substring(0, 3),
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 26.dp, start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Overview",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 20.dp, top = 20.dp)
                            )
                            Text(
                                text = movie.overview,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}