package com.storm.feedpage.navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.storm.feedpage.navigation.AppDestinations.ROUTE_COMMENTS
import com.storm.feedpage.navigation.AppDestinations.ROUTE_HOME
import com.storm.feedpage.ui.screens.home.HomeScreenUI
import com.storm.feedpage.ui.components.CommentSection
import com.storm.feedpage.ui.screens.home.HomeViewModel

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

            val postId = it.arguments?.getInt("postId") ?: -1
            Log.d(TAG, "Helllo " + postId.toString() )
            CommentSection(postId = postId, navController = navController, viewModel = viewModel)
        }

    }
}
