package com.saadeh.cinenow.list.data

import com.saadeh.cinenow.common.data.model.Movie

interface LocalDataSource {

    suspend fun getNowPLayingMovies(): List<Movie>

    suspend fun getTopRatedMovies(): List<Movie>

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getUpcomingMovies(): List<Movie>

    suspend fun updateLocalItems(movies: List<Movie>)

}