package com.saadeh.cinenow

import android.app.Application
import androidx.room.Room
import com.saadeh.cinenow.common.data.local.CineNowDatabase

class CineNowApplication: Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CineNowDatabase::class.java,"database-cine-now"
        ).build()
    }
}