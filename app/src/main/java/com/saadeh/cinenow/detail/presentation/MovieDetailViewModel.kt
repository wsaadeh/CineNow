package com.saadeh.cinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.cinenow.CineNowApplication
import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.common.data.remote.RetrofitClient
import com.saadeh.cinenow.common.data.remote.model.MovieDto
import com.saadeh.cinenow.detail.data.MovieDetailRepository
import com.saadeh.cinenow.detail.data.remote.DetailService
import com.saadeh.cinenow.di.DispatcherIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    @DispatcherIO private val dispatcher: CoroutineDispatcher //= Dispatchers.IO
) : ViewModel() {
    private val _uiMovieById = MutableStateFlow<Movie?>(null)
    val uiMovieById: StateFlow<Movie?> = _uiMovieById

    fun fetchMovieById(movieId: String) {
//        if (_uiMovieById.value == null) {
        viewModelScope.launch(dispatcher) {//Dispatchers.IO
            val response = repository.getMovieBYId(movieId)

            if (response.isSuccess) {
                val movies = response.getOrNull()
                if (movies != null) {
                    _uiMovieById.value = movies
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiMovieById.value = Movie(
                        id = 0,
                        title = "No Internet",
                        overview = "No internet Connection",
                        image = "",
                        category = MovieCategory.NowPlaying.name,
                    )
                }

            }
        }
//        }
    }

/*    fun cleanMovieId() {
        viewModelScope.launch {
            delay(1000)
            _uiMovieById.value = null
        }
    }*/

/*    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MovieDetailViewModel(
                    repository = (application as CineNowApplication).repDetail
                ) as T
            }
        }
    }*/

}