package com.saadeh.cinenow.list.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.cinenow.common.data.RetrofitClient
import com.saadeh.cinenow.list.data.ListService
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.presentation.ui.MovieListUiState
import com.saadeh.cinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUiState())
    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying

    private val _uiPopular = MutableStateFlow(MovieListUiState())
    val uiPopular: StateFlow<MovieListUiState> = _uiPopular

    private val _uiTopRated = MutableStateFlow(MovieListUiState())
    val uiTopRated: StateFlow<MovieListUiState> = _uiTopRated

    private val _uiUpcoming = MutableStateFlow(MovieListUiState())
    val uiUpcoming: StateFlow<MovieListUiState> = _uiUpcoming

    init {

        fetchNowPlayingMovies()

        fetchPopularMovies()

        fetchTopRatedMovies()

        fetchUpcomingMovies()

    }

    private fun fetchNowPlayingMovies() {
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getNowPlaying()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
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
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value = MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiNowPlaying.value = MovieListUiState(isError = true)
                }
            }
        }
    }

    private fun fetchPopularMovies() {
        _uiPopular.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getPopular()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieUiDataList = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath,
                        )
                    }
                    _uiPopular.value = MovieListUiState(list = movieUiDataList)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiPopular.value = MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiPopular.value = MovieListUiState(isError = true)
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRated.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getTopRated()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieUiListData = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath,
                        )
                    }
                    _uiTopRated.value = MovieListUiState(list = movieUiListData)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiTopRated.value = MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiTopRated.value = MovieListUiState(isError = true)
                }
            }
        }
    }

    private fun fetchUpcomingMovies() {
        _uiUpcoming.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getUpcoming()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieUiListData = movies.map { movieDto ->
                        MovieUiData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath,
                        )
                    }
                    _uiUpcoming.value = MovieListUiState(list = movieUiListData)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiUpcoming.value = MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiUpcoming.value = MovieListUiState(isError = true)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)

                return MovieListViewModel(
                    repository = MovieListRepository(listService)
                ) as T
            }
        }
    }
}