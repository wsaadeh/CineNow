package com.saadeh.cinenow.detail.data

import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.detail.data.local.DetailLocalDataSource
import com.saadeh.cinenow.detail.data.remote.DetailRemoteDataSource
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val localDataSource: DetailLocalDataSource,
    private val remoteDataSource: DetailRemoteDataSource
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