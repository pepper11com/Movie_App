package com.example.movie.domain.use_case.get_movie

import com.example.movie.common.Resource
import com.example.movie.data.remote.dto.MovieDetailDto
import com.example.movie.data.remote.dto.toMovie
import com.example.movie.data.remote.dto.toMovieDetail
import com.example.movie.domain.model.Movie
import com.example.movie.domain.model.MovieDetail
import com.example.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    operator fun invoke(movieId: String, apiKey: String): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading<MovieDetail>())
            val movies = movieRepository.getMovieDetails(movieId, apiKey).toMovieDetail()
            emit(Resource.Success<MovieDetail>(movies))
        } catch(e: HttpException) {
            emit(Resource.Error<MovieDetail>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<MovieDetail>("Couldn't reach server. Check your internet connection."))
        }
    }
}
