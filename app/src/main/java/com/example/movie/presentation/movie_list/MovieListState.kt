package com.example.movie.presentation.movie_list

import com.example.movie.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: Movie? = null,
    val error: String = ""
)
