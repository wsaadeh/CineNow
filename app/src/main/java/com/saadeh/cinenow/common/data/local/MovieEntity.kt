package com.saadeh.cinenow.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id","category"])
data class MovieEntity(
    //@PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
    val category: String,
)
