package com.saadeh.cinenow.list.data.remote

import com.saadeh.cinenow.common.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListService {
    @GET("now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @GET("popular?language=en-US&page=1")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("upcoming?language=en-US&page=1")
    suspend fun getUpcomingMovies(): Response<MovieResponse>
}