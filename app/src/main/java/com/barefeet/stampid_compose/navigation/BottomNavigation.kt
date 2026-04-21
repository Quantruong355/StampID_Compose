package com.barefeet.stampid_compose.navigation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.screens.article.ArticleScreen
import com.barefeet.stampid_compose.screens.bestmatch.BestMatchScreen
import com.barefeet.stampid_compose.screens.camera.CameraScreen
import com.barefeet.stampid_compose.screens.camera.CameraViewModel
import com.barefeet.stampid_compose.screens.collection.CollectionScreen
import com.barefeet.stampid_compose.screens.home.HomeScreen
import com.barefeet.stampid_compose.screens.home.HomeViewModel
import com.barefeet.stampid_compose.screens.iap.IAPScreen
import com.barefeet.stampid_compose.screens.loading.LoadingScreen
import com.barefeet.stampid_compose.screens.loading.StampResultViewModel
import com.barefeet.stampid_compose.screens.result.ResultScreen
import com.barefeet.stampid_compose.screens.setting.SettingScreen

@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val homeVM: HomeViewModel = hiltViewModel()
    val articleList by homeVM.uiState.collectAsState()

    val context = LocalContext.current
    val sharedResultVM: StampResultViewModel = hiltViewModel(context as ComponentActivity)

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen,
        modifier = modifier
            .background(color = colorResource(id = R.color.white_2))
    ) {
        composable<Routes.HomeScreen> {
            HomeScreen(
                homeVM = homeVM,
                onSettingClick = {
                    navController.navigate(Routes.SettingScreen)
                },
                onArticleClick = { selectedIndex ->
                    navController.navigate(Routes.ArticleScreen(articleIndex = selectedIndex))
                },
                onSearchClick = {

                },
                onIAPClick = {
                    navController.navigate(Routes.IAPScreen)
                }
            )
        }

        composable<Routes.ArticleScreen>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,animationSpec = tween(600))
            }
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.ArticleScreen>()
            val selectedIndex = route.articleIndex

            val selectedArticle = articleList.articles.getOrNull(selectedIndex)

            if (selectedArticle != null) {
                ArticleScreen(
                    article = selectedArticle,
                    onBackClick = { navController.navigateUp() }
                )
            } else {

            }
        }

        composable<Routes.CollectionScreen> {
            CollectionScreen()
        }

        composable<Routes.SettingScreen>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
            },
            exitTransition = {
                if (targetState.destination.hasRoute<Routes.IAPScreen>()) {
                    null
                } else {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
            },
            popEnterTransition = {
                null
            }
        ) {
            SettingScreen(
                onBackClick = { navController.navigateUp() },
                onIAPClick = { navController.navigate(Routes.IAPScreen)}
            )
        }

        composable<Routes.IAPScreen> (
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down,animationSpec = tween(600))
            }
        ){
            IAPScreen(
                onBackClick = { navController.navigateUp()}
            )
        }

        composable<Routes.CameraScreen> (
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(600))
            },

            exitTransition = {
                if (targetState.destination.hasRoute<Routes.LoadingScreen>()) {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
                } else {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(600))
                }
            },

            popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(600))
            },
        ){

            CameraScreen(
                onBack = { navController.navigateUp() },
                onNavigateLoading = { uri ->
//                    val imageUri = Uri.encode(uri.toString())
                    sharedResultVM.updateCapturedImage(uri)
                    navController.navigate(Routes.LoadingScreen(imageUri = null))
                }
            )
        }

        composable<Routes.LoadingScreen>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(500))
            }
        ) { backStackEntry ->

//            val loadingRoute = backStackEntry.toRoute<Routes.LoadingScreen>()
//            val decodedUri = Uri.decode(loadingRoute.imageUri)
            val decodedUri = sharedResultVM.userImageUri.value.toString()

            LoadingScreen(
                imageUri = decodedUri,
                onNavigateBack = { navController.navigateUp() },
                onNavigateToBestMatch = {
                    sharedResultVM.updateResult(it)
                    navController.navigate(Routes.ResultScreen){
                        popUpTo<Routes.LoadingScreen> { inclusive = true }
                    }
                }
            )
        }

        composable<Routes.ResultScreen>(
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(600))
            }
        ) { backStackEntry ->

            ResultScreen(
                stampResultVM = sharedResultVM
            )
        }
    }
}
