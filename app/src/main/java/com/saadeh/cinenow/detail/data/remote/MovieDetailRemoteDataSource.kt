package com.saadeh.cinenow.detail.data.remote

import android.accounts.NetworkErrorException
import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.model.Movie
import javax.inject.Inject


class MovieDetailRemoteDataSource @Inject constructor(
    private val detailService: DetailService
) : DetailRemoteDataSource {
    override suspend fun getMovieById(id: String): Result<Movie?>{

        return try {
            val response = detailService.getMovieById(id)
            if (response.isSuccessful){
                val movie = response.body()?.let {
                    Movie(
                    id = it.id,
                    title =  it.title,
                    overview =  it.overview,
                    image = it.posterFullPath,
                    category = MovieCategory.NowPlaying.name) }

                return Result.success(movie)
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }

        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}