package com.barefeet.stampid_compose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    object SplashScreen : Routes

    @Serializable
    object OnboardScreen : Routes

    @Serializable
    object MainScreen : Routes

}