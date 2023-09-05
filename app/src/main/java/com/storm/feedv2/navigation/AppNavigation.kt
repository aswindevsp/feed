package com.storm.feedv2.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.storm.feedv2.navigation.AppDestinations.ROUTE_COMMENTS
import com.storm.feedv2.navigation.AppDestinations.ROUTE_HOME
import com.storm.feedv2.ui.components.CommentSection
import com.storm.feedv2.ui.screens.home.HomeScreenUI
import com.storm.feedv2.ui.screens.home.HomeViewModel

@Composable
fun AppNavigation(
    startDestination: String = ROUTE_HOME,
) {
    val navController = rememberNavController()

    val viewModel: HomeViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(ROUTE_HOME) {
            HomeScreenUI(
                viewModel = viewModel,
                navigateToSelectedPost = {
                    navController.navigate("$ROUTE_COMMENTS/$it")
            })
        }

        composable(
            route = "$ROUTE_COMMENTS/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.IntType
                }
            )
        ) {

            CommentSection(navController = navController, viewModel = viewModel)
        }

    }
}
