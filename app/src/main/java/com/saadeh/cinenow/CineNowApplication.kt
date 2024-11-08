package com.saadeh.cinenow

import android.app.Application
import androidx.room.Room
import com.saadeh.cinenow.common.data.remote.RetrofitClient
import com.saadeh.cinenow.common.data.local.CineNowDatabase
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.ListService
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource

class CineNowApplication: Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CineNowDatabase::class.java,"database-cine-now"
        ).build()
    }

    private val listService by lazy {
        RetrofitClient.retrofitInstance.create(ListService::class.java)
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
}