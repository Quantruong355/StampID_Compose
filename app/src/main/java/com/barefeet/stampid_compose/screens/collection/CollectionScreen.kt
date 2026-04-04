package com.barefeet.stampid_compose.screens.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.barefeet.stampid_compose.R

@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    CollectionContent(modifier = modifier)
}

@Composable
fun CollectionContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2))
    ) {

    }
}

@Preview
@Composable
private fun CollectionScreenPrev() {

}