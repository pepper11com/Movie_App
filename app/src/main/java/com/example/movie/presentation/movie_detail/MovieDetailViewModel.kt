package com.example.movie.presentation.movie_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.BuildConfig
import com.example.movie.common.Constants
import com.example.movie.common.Resource
import com.example.movie.domain.use_case.get_movie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_MOVIE_ID)?.let { movieId ->
            Log.d("MovieDetailViewModel", "movieId: $movieId")
            getMovieDetail(
                movieId = movieId
            )
        }
    }

    private fun getMovieDetail(apiKey: String = BuildConfig.MOVIE_API_KEY, movieId: String) {
        Log.d("MovieDetailViewModel", "getMovieDetail()")

        getMovieDetailUseCase(movieId, apiKey).onEach {result ->
            when(result){
                is Resource.Success -> {
                    Log.d("MovieDetailViewModel", "result.data: ${result.data}")
                    _state.value = MovieDetailState(movie = result.data)
                }

                is Resource.Error -> {
                    Log.d("MovieDetailViewModel", "result.message: ${result.message}")
                    _state.value = MovieDetailState(error = result.message ?: "An unexpected error occurred, try again!")
                }

                is Resource.Loading -> {
                    Log.d("MovieDetailViewModel", "Loading...")
                    _state.value = MovieDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}