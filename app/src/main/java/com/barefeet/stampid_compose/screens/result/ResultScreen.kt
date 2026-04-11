package com.barefeet.stampid_compose.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.screens.loading.StampResultViewModel
import kotlinx.coroutines.flow.first
import java.io.File

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel,
) {

    DisposableEffect(Unit) {
        onDispose {
            stampResultVM.clearData()
        }
    }

    ResultContent(
        modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2)),
        stampResultVM = stampResultVM
    )
}

@Composable
fun ResultContent(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel
    ) {
    Column(modifier = modifier) {
        ResultHeader(
            stampResultVM = stampResultVM
        )
//        ResultBody()
    }
}

@Composable
fun ResultHeader(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Box(
        modifier = modifier
            .height(305.dp)
            .background(colorResource(R.color.green_5))
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->
            when (page) {
                0 -> {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(stampResultVM.userImageUri)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier.align(Alignment.TopCenter),
                        contentDescription = null,
                    )
                }

                else -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(stampResultVM.scanResult.value.first().image)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .scale(0.6f)
                            .align(Alignment.TopCenter),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResultPreview() {
    ResultContent(
        stampResultVM = StampResultViewModel()
    )
}