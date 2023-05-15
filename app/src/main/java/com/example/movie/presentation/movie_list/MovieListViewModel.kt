package com.example.movie.presentation.movie_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.BuildConfig
import com.example.movie.common.Resource
import com.example.movie.domain.use_case.get_movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    private val _state = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    fun getMovies(apiKey: String = BuildConfig.MOVIE_API_KEY, query: String) {
        getMoviesUseCase(apiKey, query).onEach {result ->
            when(result){
                is Resource.Success -> {
                    _state.value = MovieListState(movies = result.data)
                }
                is Resource.Error -> {
                    _state.value = MovieListState(error = result.message ?: "An unexpected error occurred, try again!")
                }

                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}