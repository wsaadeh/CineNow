package com.saadeh.cinenow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CineNowApplication : Application()
/*
{

    private val db by lazy {
        CineNowServiceLocator.createDb(this)
    }

    val repository by lazy {
        CineNowServiceLocator.getRepository(db)
    }

    val repDetail by lazy {
        CineNowServiceLocator.getDetailRepository(db)
    }

}*/
