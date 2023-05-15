package com.example.movie.data.repository

import com.example.movie.data.remote.MovieApi
import com.example.movie.data.remote.dto.MovieDetailDto
import com.example.movie.data.remote.dto.MovieListDto
import com.example.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun searchMovies(apiKey: String, query: String) : MovieListDto {
        return api.searchMovies(apiKey, query)
    }

    override suspend fun getMovieDetails(movieId: String, apiKey: String) : MovieDetailDto {
        return api.getMovieDetails(movieId, apiKey)
    }

}