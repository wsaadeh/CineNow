package com.saadeh.cinenow.detail.data.local

import com.saadeh.cinenow.common.data.local.MovieDao
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.detail.data.remote.DetailRemoteDataSource
import javax.inject.Inject

class MovieDetailLocalDataSource @Inject constructor(
    private val dao: MovieDao
) : DetailLocalDataSource {
    override suspend fun getMovieById(Id: String): Movie{
        val movie = dao.getMovieById(id = Id)
        val mvResult = Movie(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            image = movie.image,
            category = movie.category,
        )
        return mvResult
    }
}