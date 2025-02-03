package com.saadeh.cinenow.detail.data.remote

import com.saadeh.cinenow.common.data.model.Movie

interface DetailRemoteDataSource {

    suspend fun getMovieById(id: String): Result<Movie?>
}