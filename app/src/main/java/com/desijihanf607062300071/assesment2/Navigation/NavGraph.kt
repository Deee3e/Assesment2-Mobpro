package com.desijihanf607062300071.assesment2.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.desijihanf607062300071.assesment2.ui.screen.DetailScreen
import com.desijihanf607062300071.assesment2.ui.screen.ListScreen


@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            ListScreen(
                navController = navController,
            )
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(
                navController = navController,
                catatanId = null,
            )
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_CATATAN) { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(KEY_ID_CATATAN)
            DetailScreen(
                navController = navController,
                catatanId = id,
            )
        }
        composable(route = Screen.RecycleBin.route) {
            RecycleBinScreen(navController = navController)
        }
    }
}

@Composable
fun RecycleBinScreen(navController: NavHostController) {

}
