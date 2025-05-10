package com.desijihanf607062300071.assesment2.Navigation

import Screen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.desijihanf607062300071.assesment2.ui.screen.DetailScreen
import com.desijihanf607062300071.assesment2.ui.screen.MainScreen
import com.desijihanf607062300071.assesment2.ui.screen.TrashScreen

const val KEY_MOVIE_ID = "movieId"

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_MOVIE_ID) { type = NavType.LongType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_MOVIE_ID)
            DetailScreen(navController, id)
        }
        composable(route = Screen.TrashScreen.route) {
            TrashScreen(navController)
        }
    }
}