package com.saadeh.cinenow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.saadeh.cinenow.detail.presentation.MovieDetailViewModel
import com.saadeh.cinenow.list.presentation.MovieListViewModel
import com.saadeh.cinenow.ui.theme.CineNowTheme

class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<MovieListViewModel> { MovieListViewModel.Factory }
    private val detailViewModel by viewModels<MovieDetailViewModel> { MovieDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CineNowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CineNowApp(
                        listViewModel = listViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}


