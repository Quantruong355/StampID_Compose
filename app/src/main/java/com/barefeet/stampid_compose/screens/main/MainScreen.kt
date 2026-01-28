package com.barefeet.stampid_compose.screens.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.BottomBarCurved
import com.barefeet.stampid_compose.navigation.BottomNavGraph
import com.barefeet.stampid_compose.navigation.Routes

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val bottomNavController = rememberNavController()
    val currentBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination

    val showBottomBar = currentRoute?.hasRoute<Routes.HomeScreen>() == true ||
            currentRoute?.hasRoute<Routes.CollectionScreen>() == true

    Scaffold(
        containerColor = colorResource(R.color.white_2),
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = { /* Action */ },
                    shape = CircleShape,
                    containerColor = colorResource(R.color.green_2),
                    modifier = Modifier
                        .size(width = 56.dp, height = 56.dp)
                        .offset(y = 50.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(bottomNavController, currentRoute)
            }

        }
    ) { padding ->
        BottomNavGraph(
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            bottomNavController
        )
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    MainScreen()
}