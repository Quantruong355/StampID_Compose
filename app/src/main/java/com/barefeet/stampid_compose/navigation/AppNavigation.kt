package com.barefeet.stampid_compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barefeet.stampid_compose.screens.onboard.OnboardScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier,startDestination: Routes) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Routes.OnboardScreen> {
            OnboardScreen(
                onFinish = { navController.navigate(Routes.MainScreen){
                    popUpTo(Routes.OnboardScreen) {
                        inclusive = true
                    }
                } }
            )
        }

        composable<Routes.MainScreen> { backStackEntry ->

        }
    }
}