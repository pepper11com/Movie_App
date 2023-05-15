package com.example.movie.data.remote.dto

import com.example.movie.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("results")
    val results: List<MovieDetailDto>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)


fun MovieListDto.toMovie(): Movie {
    return Movie(
        results = results.map { it.toMovieDetail() },
        page = page,
        totalPages = totalPages,
        totalResults = totalResults
    )
}
