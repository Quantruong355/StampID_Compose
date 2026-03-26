package com.barefeet.stampid_compose.screens.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    LoadingContent()
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Column(){

    }
}

@Composable
fun ScanningView(modifier: Modifier = Modifier) {
    Box(){

    }
}

@Preview
@Composable
private fun LodaingScreenPreview() {
    LoadingContent(
        modifier = Modifier
    )
}