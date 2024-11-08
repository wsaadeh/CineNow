package com.saadeh.cinenow.list.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.saadeh.cinenow.common.model.MovieResponse
import com.saadeh.cinenow.list.presentation.ui.MovieListUiState
import com.saadeh.cinenow.list.presentation.ui.MovieUiData
import java.net.UnknownHostException

class MovieListRepository(
    private val listService: ListService
) {
    suspend fun getNowPlaying(): Result<MovieResponse?>{
        return try {
            val response = listService.getNowPlayingMovies()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<MovieResponse?>{
        return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRated(): Result<MovieResponse?>{
        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpcoming(): Result<MovieResponse?>{
        return try {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful){
                Result.success(response.body())
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}