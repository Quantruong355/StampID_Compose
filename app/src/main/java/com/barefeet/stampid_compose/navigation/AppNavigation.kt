package com.barefeet.stampid_compose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barefeet.stampid_compose.screens.main.MainScreen
import com.barefeet.stampid_compose.screens.onboard.OnboardScreen
import com.barefeet.stampid_compose.screens.splash.SplashScreen
import com.barefeet.stampid_compose.screens.splash.SplashViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val splashViewModel: SplashViewModel = viewModel()
    val startDestination by splashViewModel.startDestination.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen,
        modifier = Modifier.background(Color.White).safeDrawingPadding()
    ) {

        composable<Routes.SplashScreen> {
            SplashScreen(
                targetRoute = startDestination,
                navController = navController
            )
        }

        composable<Routes.OnboardScreen> {
            OnboardScreen(
                modifier = modifier,
                onFinish = { navController.navigate(Routes.MainScreen){
                    popUpTo(Routes.OnboardScreen) {
                        inclusive = true
                    }
                } }
            )
        }

        composable<Routes.MainScreen>{ backStackEntry ->
            MainScreen()
        }
    }
}