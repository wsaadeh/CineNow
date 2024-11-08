package com.saadeh.cinenow.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([MovieEntity::class], version = 1)
abstract class CineNowDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}