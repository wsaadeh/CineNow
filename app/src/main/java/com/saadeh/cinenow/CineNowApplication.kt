package com.saadeh.cinenow

import android.app.Application
import androidx.room.Room
import com.saadeh.cinenow.common.data.remote.RetrofitClient
import com.saadeh.cinenow.common.data.local.CineNowDatabase
import com.saadeh.cinenow.detail.data.MovieDetailRepository
import com.saadeh.cinenow.detail.data.local.MovieDetailLocalDataSource
import com.saadeh.cinenow.detail.data.remote.DetailService
import com.saadeh.cinenow.detail.data.remote.MovieDetailRemoteDataSource
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.ListService
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource

class CineNowApplication: Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CineNowDatabase::class.java,"database-cine-now"
        ).build()
    }

    private val listService by lazy {
        RetrofitClient.retrofitInstance.create(ListService::class.java)
    }

    private val detailService by lazy {
        RetrofitClient.retrofitInstance.create(DetailService::class.java)
    }

    private val localDataSource: MovieListLocalDataSource by lazy {
        MovieListLocalDataSource(db.getMovieDao())
    }

    private val remoteDataSource: MovieListRemoteDataSource by lazy {
        MovieListRemoteDataSource(listService)
    }

    val repository: MovieListRepository by lazy {
        MovieListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }

    private val localDetailDataSource: MovieDetailLocalDataSource by lazy {
        MovieDetailLocalDataSource(db.getMovieDao())
    }

    private val remoteDetailDataSource: MovieDetailRemoteDataSource by lazy {
        MovieDetailRemoteDataSource(detailService)
    }

    val repDetail: MovieDetailRepository by lazy {
        MovieDetailRepository(
            localDataSource = localDetailDataSource,
            remoteDataSource = remoteDetailDataSource
        )
    }
}