package com.saadeh.cinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.cinenow.common.data.RetrofitClient
import com.saadeh.cinenow.common.model.MovieDto
import com.saadeh.cinenow.list.data.ListService
import com.saadeh.cinenow.list.presentation.ui.MovieListUiState
import com.saadeh.cinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUiState())
    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying

    private val _uiPopular = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiPopular: StateFlow<List<MovieDto>> = _uiPopular

    private val _uiTopRated = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiTopRated: StateFlow<List<MovieDto>> = _uiTopRated

    private val _uiUpcoming = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiUpcoming: StateFlow<List<MovieDto>> = _uiUpcoming

    init {

        fetchNowPlayingMovies()

//        fetchPopularMovies()

//        fetchTopRatedMovies()

//        fetchUpcomingMovies()

    }

    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getNowPlayingMovies()
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        val movieUiDataList = movies.map { movieDto ->
                            MovieUiData(
                                id = movieDto.id,
                                title = movieDto.title,
                                overview = movieDto.overview,
                                image = movieDto.posterFullPath
                            )
                        }
                        _uiNowPlaying.value = MovieListUiState(list = movieUiDataList)

                    }
                } else {
                    _uiNowPlaying.value = MovieListUiState(isError = true)
                    Log.d("MovieListViewModel", "Request Error:: ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if(ex is UnknownHostException){
                    _uiNowPlaying.value = MovieListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                        )
                }else{
                    _uiNowPlaying.value = MovieListUiState(isError = true)
                }

            }
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiPopular.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error:: ${response.errorBody()}")
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiTopRated.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error:: ${response.errorBody()}")
            }
        }
    }

    private fun fetchUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiUpcoming.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Error:: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(
                    listService
                ) as T
            }
        }
    }
}