package com.saadeh.cinenow.list.data

import android.accounts.NetworkErrorException
import androidx.room.Dao
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.common.data.remote.model.MovieResponse
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.ListService
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource

class MovieListRepository(
    private val local: LocalDataSource,//MovieListLocalDataSource,
    private val remote: MovieListRemoteDataSource,
) {
    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val result = remote.getNowPlaying()

            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                //Source of truth
                return Result.success(local.getNowPLayingMovies())
            } else {
                val localData = local.getNowPLayingMovies()
                if (localData.isEmpty()) {
                    return result
                } else {
                    Result.success(localData)
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<List<Movie>?> {
        return try {
            val result = remote.getPopular()

            if (result.isSuccess) {
                val moviesRemote = result.getOrNull() ?: emptyList()
                if (moviesRemote.isNotEmpty()) {
                    local.updateLocalItems(moviesRemote)
                }
                //Source of thuth
                return Result.success(local.getPopularMovies())
            } else {
                val localData = local.getPopularMovies()
                if (localData.isEmpty()) {
                    return result
                } else {
                    Result.success(localData)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getTopRated(): Result<List<Movie>?> {
        return try {
            val result = remote.getTopRated()
            if (result.isSuccess) {
                val remoteMovie = result.getOrNull() ?: emptyList()
                if (remoteMovie.isNotEmpty()) {
                    local.updateLocalItems(remoteMovie)
                }
                return Result.success(local.getTopRatedMovies())
            } else {
                val localData = local.getTopRatedMovies()
                if (localData.isEmpty()) {
                    return result
                } else {
                    Result.success(localData)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpcoming(): Result<List<Movie>?> {
        return try {
            val apiResult = remote.getUpcoming()

            if (apiResult.isSuccess){
                val remoteMovie = apiResult.getOrNull() ?: emptyList()
                if (remoteMovie.isNotEmpty()){
                    local.updateLocalItems(remoteMovie)
                }
                return Result.success(local.getUpcomingMovies())
            }else{
                val localData = local.getUpcomingMovies()
                if (localData.isEmpty()){
                    return apiResult
                } else {
                    Result.success(localData)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}