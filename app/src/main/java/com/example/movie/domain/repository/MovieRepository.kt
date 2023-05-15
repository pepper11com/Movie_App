package com.example.movie.domain.repository

import com.example.movie.data.remote.dto.MovieDetailDto
import com.example.movie.data.remote.dto.MovieListDto

interface MovieRepository {

    suspend fun searchMovies(apiKey: String, query: String) : MovieListDto

    suspend fun getMovieDetails(movieId: String, apiKey: String) : MovieDetailDto

}