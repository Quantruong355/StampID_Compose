package com.barefeet.stampid_compose.screens.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.BottomBarCurved
import com.barefeet.stampid_compose.navigation.Routes

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: NavDestination
    ) {
    Surface(
        color = Color.White,
        shape = BottomBarCurved(),
        shadowElevation = 0.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent
        ) {
            NavigationBarItem(
                icon = { Icon(painter = painterResource(R.drawable.home_icon), contentDescription = null) },
                label = {
                    Text(
                        stringResource(R.string.bottombar_text1),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.onest_regular))
                        )
                    )
                },
                selected = currentRoute.hierarchy.any {
                    it.hasRoute<Routes.HomeScreen>()
                } == true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(id = R.color.green_2),
                    unselectedIconColor = colorResource(id= R.color.gray_1),
                    selectedTextColor = colorResource(id = R.color.green_2),
                    unselectedTextColor = colorResource(id = R.color.gray_1)
                ),
                onClick = {
                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            NavigationBarItem(
                selected = false,
                icon = {},
                label = {},
                onClick = {}
            )

            NavigationBarItem(
                icon = { Icon(painter = painterResource(R.drawable.collection_icon), contentDescription = null)},
                label = {
                    Text(
                        stringResource(R.string.bottombar_text2),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.onest_regular))
                        )
                    )
                },
                selected = currentRoute.hierarchy.any {
                    it.hasRoute<Routes.CollectionScreen>()
                } == true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(id = R.color.green_2),
                    unselectedIconColor = colorResource(id= R.color.gray_1),
                    selectedTextColor = colorResource(id = R.color.green_2),
                    unselectedTextColor = colorResource(id = R.color.gray_1)
                ),
                onClick = {
                    navController.navigate(Routes.CollectionScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}