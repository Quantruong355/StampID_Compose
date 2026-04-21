package com.barefeet.stampid_compose.screens.result

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
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

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barefeet.stampid_compose.model.StampDataResponse

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel,
) {
    val userImageUri by stampResultVM.userImageUri.collectAsStateWithLifecycle()
    val scanResults by stampResultVM.scanResult.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            stampResultVM.clearData()
        }
    }

    ResultContent(
        modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2)),
        userImageUri = userImageUri,
        scanResults = scanResults
    )
}

@Composable
fun ResultContent(
    modifier: Modifier = Modifier,
    userImageUri: Uri?,
    scanResults: List<StampDataResponse>
    ) {
    Column(modifier = modifier) {
        ResultHeader(
            userImageUri = userImageUri,
            scanResults = scanResults
        )
//        ResultBody()
    }
}

@Composable
fun ResultHeader(
    modifier: Modifier = Modifier,
    userImageUri: Uri?,
    scanResults: List<StampDataResponse>
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Box(
        modifier = modifier
            .height(305.dp)
            .background(colorResource(R.color.green_5))
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (page) {
                    0 -> {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userImageUri)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier
                                .size(width = 154.dp, height = 180.dp)
                                .padding(bottom = 20.dp)
                            ,
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                        )
                    }

                    else -> {
                        if (scanResults.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(scanResults.first().image)
                                    .crossfade(true)
                                    .build(),
                                modifier = Modifier
                                    .size(width = 154.dp, height = 180.dp)
                                    .padding(bottom = 20.dp)
                                ,
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResultPreview() {
    ResultContent(
        userImageUri = null,
        scanResults = emptyList()
    )
}