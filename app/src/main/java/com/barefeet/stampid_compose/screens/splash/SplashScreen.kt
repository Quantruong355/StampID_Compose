package com.barefeet.stampid_compose.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barefeet.stampid_compose.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.icon_app),
            contentDescription = null,
            modifier = Modifier
                .scale(0.6f)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )

    }
}

@Preview
@Composable
private fun SplashScreenPrev() {
    SplashScreen()
}