package com.saadeh.cinenow.list

import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.list.data.LocalDataSource

class FakeMovieListLocalDataSource : LocalDataSource {

    var nowPlaying = emptyList<Movie>()
    override suspend fun getNowPLayingMovies(): List<Movie> {
        return nowPlaying
    }

    var topRated = emptyList<Movie>()
    override suspend fun getTopRatedMovies(): List<Movie> {
        return topRated
    }

    var popular = emptyList<Movie>()
    override suspend fun getPopularMovies(): List<Movie> {
        return popular
    }

    var upcoming = emptyList<Movie>()
    override suspend fun getUpcomingMovies(): List<Movie> {
        return upcoming
    }

    var updateItems = emptyList<Movie>()
    override suspend fun updateLocalItems(movies: List<Movie>) {
        updateItems = movies
    }
}