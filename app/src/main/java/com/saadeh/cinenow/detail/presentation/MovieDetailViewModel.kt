package com.saadeh.cinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.cinenow.common.data.RetrofitClient
import com.saadeh.cinenow.common.model.MovieDto
import com.saadeh.cinenow.common.model.MovieResponse
import com.saadeh.cinenow.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MovieDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {
    private val _uiMovieById = MutableStateFlow<MovieDto?>(null)
    val uiMovieById: StateFlow<MovieDto?> = _uiMovieById

    fun fetchMovieById(movieId: String) {
//        if (_uiMovieById.value == null) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailService.getMovieById(movieId)

            if (response.isSuccessful) {
                val movies = response.body()
                if (movies != null) {
                    _uiMovieById.value = movies
                }
            } else {
                Log.d("MovieDetailViewModel", "Request Error:: ${response.errorBody()}")
            }
        }
//        }
    }

    fun cleanMovieId() {
        viewModelScope.launch {
            delay(1000)
            _uiMovieById.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(detailService) as T
            }
        }
    }

}