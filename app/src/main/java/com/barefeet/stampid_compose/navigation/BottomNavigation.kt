package com.barefeet.stampid_compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
import com.barefeet.stampid_compose.screens.setting.SettingScreen

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
            HomeScreen(
                onSettingClick = {
                    navController.navigate(Routes.SettingScreen)
                },
                onArticleClick = {
//                    navController.navigate(Routes.ArticleDetailScreen)
                },
                onSearchClick = {

                }
            )
        }

        composable<Routes.CollectionScreen> {
            CollectionScreen()
        }

        composable<Routes.SettingScreen>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,animationSpec = tween(600))
            },
        ) {
            SettingScreen(
                onBackClick = { navController.navigateUp()}
            )
        }

    }
}