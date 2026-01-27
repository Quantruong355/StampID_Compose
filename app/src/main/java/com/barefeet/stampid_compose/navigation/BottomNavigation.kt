package com.barefeet.stampid_compose.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.screens.collection.CollectionScreen
import com.barefeet.stampid_compose.screens.home.HomeScreen

@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
        modifier = modifier
            .background(color = colorResource(id = R.color.white_2))
    ) {
        composable<Routes.HomeScreen> {
            HomeScreen()
        }
        composable<Routes.CollectionScreen> {
            CollectionScreen()
        }
    }
}