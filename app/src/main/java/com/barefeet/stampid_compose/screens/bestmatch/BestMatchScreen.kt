package com.barefeet.stampid_compose.screens.bestmatch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.utils.noRippleClickable


@Composable
fun BestMatchScreen(modifier: Modifier = Modifier) {

    BestMatchContent(modifier = modifier)
}

@Composable
fun BestMatchContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2))
    ){
        Box(
            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.white))
        ) {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .noRippleClickable{  }
            )
        }
    }
}