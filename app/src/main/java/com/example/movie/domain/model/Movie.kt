package com.example.movie.domain.model


data class Movie (
    val results: List<MovieDetail>,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int
)