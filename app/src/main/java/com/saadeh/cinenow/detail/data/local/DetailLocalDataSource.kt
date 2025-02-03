package com.saadeh.cinenow.detail.data.local

import com.saadeh.cinenow.common.data.model.Movie

interface DetailLocalDataSource {

    suspend fun getMovieById(Id: String): Movie
}