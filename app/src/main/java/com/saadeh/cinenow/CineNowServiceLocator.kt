package com.saadeh.cinenow

import android.app.Application
import androidx.room.Room
import com.saadeh.cinenow.common.data.local.CineNowDatabase
import com.saadeh.cinenow.common.data.remote.RetrofitClient
import com.saadeh.cinenow.detail.data.MovieDetailRepository
import com.saadeh.cinenow.detail.data.local.DetailLocalDataSource
import com.saadeh.cinenow.detail.data.local.MovieDetailLocalDataSource
import com.saadeh.cinenow.detail.data.remote.DetailRemoteDataSource
import com.saadeh.cinenow.detail.data.remote.DetailService
import com.saadeh.cinenow.detail.data.remote.MovieDetailRemoteDataSource
import com.saadeh.cinenow.list.data.local.LocalDataSource
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.ListService
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource
import com.saadeh.cinenow.list.data.remote.RemoteDataSource

object CineNowServiceLocator {

    fun createDb(application: Application): CineNowDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            CineNowDatabase::class.java, "database-cine-now"
        ).build()
    }

    fun getRepository(db: CineNowDatabase): MovieListRepository {


        val listService =
            RetrofitClient.retrofitInstance.create(ListService::class.java)


        val localDataSource: LocalDataSource =
            MovieListLocalDataSource(db.getMovieDao())

        val remoteDataSource: RemoteDataSource =
            MovieListRemoteDataSource(listService)

        return MovieListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )

    }

    fun getDetailRepository(db: CineNowDatabase): MovieDetailRepository {

        val detailService =
            RetrofitClient.retrofitInstance.create(DetailService::class.java)

        val localDetailDataSource: DetailLocalDataSource =
            MovieDetailLocalDataSource(db.getMovieDao())


        val remoteDetailDataSource: DetailRemoteDataSource =
            MovieDetailRemoteDataSource(detailService)


        return MovieDetailRepository(
            localDataSource = localDetailDataSource,
            remoteDataSource = remoteDetailDataSource
        )

    }


}