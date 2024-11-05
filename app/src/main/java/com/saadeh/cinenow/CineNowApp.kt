package com.saadeh.cinenow

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saadeh.cinenow.detail.presentation.MovieDetailViewModel
import com.saadeh.cinenow.detail.presentation.ui.MovieDetailScreen
import com.saadeh.cinenow.list.presentation.MovieListViewModel
import com.saadeh.cinenow.list.presentation.ui.MovieListScreen

@Composable
fun CineNowApp(
    listViewModel: MovieListViewModel,
    detailViewModel: MovieDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList" ){
        composable(route = "movieList"){
            MovieListScreen(navController, listViewModel)
        }
        composable(
            route = "movieDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            MovieDetailScreen(movieId, navController, detailViewModel)
        }
    }
}