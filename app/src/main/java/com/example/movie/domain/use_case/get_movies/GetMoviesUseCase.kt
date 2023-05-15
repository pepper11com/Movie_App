package com.example.movie.domain.use_case.get_movies

import com.example.movie.common.Resource
import com.example.movie.data.remote.dto.toMovie
import com.example.movie.domain.model.Movie
import com.example.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(apiKey: String, query: String): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading<Movie>())
            val movies = movieRepository.searchMovies(apiKey, query).toMovie()
            emit(Resource.Success<Movie>(movies))
        } catch(e: HttpException) {
            emit(Resource.Error<Movie>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<Movie>("Couldn't reach server. Check your internet connection."))
        }
    }
}