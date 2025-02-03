package com.saadeh.cinenow.detail.presentation.di

import com.saadeh.cinenow.detail.data.local.DetailLocalDataSource
import com.saadeh.cinenow.detail.data.local.MovieDetailLocalDataSource
import com.saadeh.cinenow.detail.data.remote.DetailRemoteDataSource
import com.saadeh.cinenow.detail.data.remote.DetailService
import com.saadeh.cinenow.detail.data.remote.MovieDetailRemoteDataSource
import com.saadeh.cinenow.list.data.local.LocalDataSource
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.ListService
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource
import com.saadeh.cinenow.list.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class MovieDetailModule {


    @Provides
    fun providesDetailService(retrofit: Retrofit): DetailService {
        return retrofit.create(DetailService::class.java)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
interface MovieDetailModuleBinding {

    @Binds
    fun bindDetailLocalDataSource(impl: MovieDetailLocalDataSource) : DetailLocalDataSource

    @Binds
    fun bindDetailRemoteDataSource(impl: MovieDetailRemoteDataSource): DetailRemoteDataSource


}