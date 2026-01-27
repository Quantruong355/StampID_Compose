package com.barefeet.stampid_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.barefeet.stampid_compose.navigation.AppNavigation
import com.barefeet.stampid_compose.navigation.Routes
import com.barefeet.stampid_compose.screens.onboard.OnboardingManager
import com.barefeet.stampid_compose.screens.splash.SplashViewModel
import com.barefeet.stampid_compose.ui.theme.StampIDComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // 2. Giữ Splash Screen hiển thị cho đến khi load xong data
//        splashScreen.setKeepOnScreenCondition {
//            viewModel.isLoading.value
//        }

        enableEdgeToEdge()
        setContent {
            StampIDComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Chỉ vẽ NavHost khi đã load xong (để startDestination đúng)
//                    if (!viewModel.isLoading.collectAsState().value) {
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StampIDComposeTheme {
        Greeting("Android")
    }
}