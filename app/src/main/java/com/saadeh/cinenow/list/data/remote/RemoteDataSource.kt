package com.saadeh.cinenow.list.data.remote


import com.saadeh.cinenow.common.data.model.Movie

interface RemoteDataSource {

    suspend fun getNowPlaying(): Result<List<Movie>?>

    suspend fun getPopular(): Result<List<Movie>?>

    suspend fun getTopRated(): Result<List<Movie>?>

    suspend fun getUpcoming(): Result<List<Movie>?>
}