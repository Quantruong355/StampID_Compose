package com.barefeet.stampid_compose.screens.result

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.screens.loading.StampResultViewModel
import com.barefeet.stampid_compose.UI_Common.PriceBoard
import com.barefeet.stampid_compose.UI_Common.StatusImageButton
import com.barefeet.stampid_compose.model.StampAttributes
import com.barefeet.stampid_compose.model.StampDataResponse
import com.barefeet.stampid_compose.ui.theme.AppTypography
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel,
) {
    val userImageUri by stampResultVM.userImageUri.collectAsStateWithLifecycle()
    val scanResults by stampResultVM.scanResult.collectAsStateWithLifecycle()
    val stampResult = scanResults.firstOrNull()

    DisposableEffect(Unit) {
        onDispose {
            stampResultVM.clearData()
        }
    }

    if (stampResult != null) {
        ResultContent(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.white_2)),
            userImageUri = userImageUri,
            stampResult = stampResult,
        )
    } else {

    }
}

@Composable
fun ResultContent(
    modifier: Modifier = Modifier,
    userImageUri: Uri?,
    stampResult: StampDataResponse
) {

    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
        ) {
            ResultHeader(
                userImageUri = userImageUri,
                stampResult = stampResult
            )
            ResultBody(
                stampResult = stampResult
            )
        }

        FilledIconButton(
            onClick = { },
            modifier = Modifier
                .padding(15.dp)
                .size(44.dp)
                .align(Alignment.TopEnd),
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.White
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }

}

@Composable
fun ResultHeader(
    modifier: Modifier = Modifier,
    userImageUri: Uri?,
    stampResult: StampDataResponse
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val isYoursActive = pagerState.currentPage == 0
    val isRefActive = pagerState.currentPage == 1
    val bottomRoundedShape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)

    Box(
        modifier = modifier
            .height(300.dp)
            .background(
                colorResource(R.color.green_5),
                shape = bottomRoundedShape
            )
            .border(
                width = 1.dp,
                color = colorResource(R.color.gray_3),
                shape = bottomRoundedShape
            )
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp), contentAlignment = Alignment.Center
            ) {
                when (page) {
                    0 -> {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(userImageUri)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier
                                .size(width = 154.dp, height = 180.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                        )
                    }

                    else -> {
                        if (stampResult != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(stampResult.image)
                                    .crossfade(true)
                                    .build(),
                                modifier = Modifier
                                    .size(width = 154.dp, height = 180.dp),
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            StatusImageButton(
                text = stringResource(R.string.result_text1),
                isActive = isYoursActive,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(0) }
                }
            )

            StatusImageButton(
                text = stringResource(R.string.result_text2),
                isActive = isRefActive,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(1) }
                }
            )
        }
    }
}


@Composable
fun ResultBody(
    stampResult: StampDataResponse
) {

    Text(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .padding(top= 10.dp)
        ,
        text = stampResult.name,
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.Black,
            fontSize = 16.sp
        )
    )

    Text(
        modifier = Modifier.padding(horizontal = 10.dp),
        text = stampResult.attributes.description,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = colorResource(R.color.gray_2),
            lineHeight = 20.sp
        )
    )


    PriceBoard(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
        currency = stampResult.attributes.currency,
        minPrice = stampResult.attributes.minPrice,
        maxPrice = stampResult.attributes.maxPrice
    )

    MarketOfferSection()

}

@Composable
fun MarketOfferSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(top= 10.dp)
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.result_text4),
                style = AppTypography.OnestSemiBold.copy(
                    color = Color.Black,
                    fontSize = 15.sp
                )
            )

            Text(
                modifier = Modifier.padding(top= 5.dp),
                text = stringResource(R.string.result_text5),
                style = AppTypography.OnestRegular.copy(
                    color = colorResource(R.color.gray_2),
                    fontSize = 13.sp
                )
            )
        }

        Text(
            text = stringResource(R.string.result_text6),
            style = AppTypography.OnestMedium.copy(
                color = colorResource(R.color.purple_1),
                fontSize = 14.sp
            )
        )


    }
}

@Preview
@Composable
private fun ResultPreview() {
    ResultContent(
        userImageUri = null,
        stampResult = StampDataResponse(
            name = "",
            description = "",
            image = "",
            productPrice = "",
            attributes = StampAttributes(
                country = "United Kingdom",
                issuedOn = "",
                color = "",
                faceValue = "",
                series = "",
                minPrice = 1000.0,
                maxPrice = 2000.0,
                condition = "",
                currency = "",
                description = ""
            ),
            marketItems = emptyList()
        )
    )
}