package com.saadeh.cinenow.list.data.local

import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.local.MovieDao
import com.saadeh.cinenow.common.data.local.MovieEntity
import com.saadeh.cinenow.common.data.model.Movie

class MovieListLocalDataSource(
    private val dao: MovieDao
) {

    suspend fun getNowPLayingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.NowPlaying)
    }

    suspend fun getPopularMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.Popular)
    }

    suspend fun getTopRatedMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.TopRated)
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.Upcoming)
    }

    suspend fun  updateLocalItems(movies:List<Movie>){
        val entities = movies.map {
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
        dao.insertAll(entities)
    }

    private suspend fun getMoviesByCategory(movieCategory: MovieCategory): List<Movie> {
        val entities = dao.getMoviesByCategory(movieCategory.name)

        return entities.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category,
            )
        }
    }
}