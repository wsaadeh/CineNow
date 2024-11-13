package com.saadeh.cinenow.detail.data

import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.detail.data.local.MovieDetailLocalDataSource
import com.saadeh.cinenow.detail.data.remote.MovieDetailRemoteDataSource
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource

class MovieDetailRepository(
    private val localDataSource: MovieDetailLocalDataSource,
    private val remoteDataSource: MovieDetailRemoteDataSource
) {
    suspend fun getMovieBYId(id: String): Result<Movie?>{
        return try {
            val apiResult = remoteDataSource.getMovieById(id)

            if (apiResult.isSuccess){
                val detailRemote = apiResult.getOrNull()
                if (detailRemote != null){
                    return Result.success(detailRemote)
                }else{
                    return Result.success(localDataSource.getMovieById(id))
                }
            }else{
                val localData = localDataSource.getMovieById(id)
                return Result.success(localData)
            }
        } catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }

    }

}