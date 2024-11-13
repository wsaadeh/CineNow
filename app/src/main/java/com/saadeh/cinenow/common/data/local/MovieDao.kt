package com.saadeh.cinenow.common.data.local

import android.graphics.Movie
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("Select * from movieentity where category = :category")
    fun getMoviesByCategory(category: String): List<MovieEntity>

    @Query("Select * from movieentity where Id = :id")
    fun getMovieById(id: String): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

}