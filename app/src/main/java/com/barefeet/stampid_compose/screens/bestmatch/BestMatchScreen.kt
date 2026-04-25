package com.barefeet.stampid_compose.screens.bestmatch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.model.StampAttributes
import com.barefeet.stampid_compose.model.StampDataResponse
import com.barefeet.stampid_compose.screens.loading.StampResultViewModel
import com.barefeet.stampid_compose.utils.clickableSafe
import com.barefeet.stampid_compose.utils.noRippleClickable


@Composable
fun BestMatchScreen(
    modifier: Modifier = Modifier,
    stampResultVM: StampResultViewModel,
    onBackClick : () -> Unit
    ) {

    val results by stampResultVM.scanResult.collectAsStateWithLifecycle()


    DisposableEffect(Unit) {
        onDispose {
            stampResultVM.clearData()
        }
    }

    BestMatchContent(
        modifier = modifier,
        results = results,
        onBackClick = onBackClick
    )
}

@Composable
fun BestMatchContent(
    modifier: Modifier = Modifier,
    results: List<StampDataResponse>,
    onBackClick : () -> Unit
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2))
    ){
        BestmatchHeader(
            modifier = modifier,
            onBackClick = onBackClick
        )

        BestmatchBody(
            modifier = modifier.padding(10.dp),
            results = results,
        )

    }
}

@Composable
fun BestmatchHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white))
            .padding(vertical = 15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 15.dp)
                .clickableSafe(showRipple = false) { onBackClick() }
        )

        Text(
            text = stringResource(R.string.bestmatch_text1),
            color = Color.Black,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun BestmatchBody(
    modifier: Modifier = Modifier,
    results: List<StampDataResponse>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ){
        itemsIndexed(results) { index, stamp ->
            if (index == 0) {
                BestMatchItem(stamp = stamp)
            } else {
//                StandardStampItem(stamp = stamp)
            }
        }
    }
}

@Composable
fun BestMatchItem(
    stamp : StampDataResponse
) {

    val startColor = colorResource(id = R.color.green_3)
    val endColor = colorResource(id = R.color.green_2)

    Card(
        modifier = Modifier.fillMaxWidth().height(240.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(
                    listOf(startColor,endColor),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)))

        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)

            )
            {
                Image(
                    painter = painterResource(id = R.drawable.laurel_wreath_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 194.dp, height = 103.dp)
                        .align(Alignment.Center)
                        .offset(y = -5.dp)
                )

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(180.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.6f), Color.Transparent)
                        )
                    )
            )

                AsyncImage(
                    model = stamp.image,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .align(Alignment.Center)
                        .fillMaxSize(0.4f)
                        .offset(y = -5.dp)
                    ,
                    contentScale =ContentScale.Fit
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (37).dp)

            ){
                Image(
                    painter = painterResource(id = R.drawable.fade_bg2),
                    contentDescription = null,
                    modifier = Modifier.size(width = 156.dp, height = 26.dp),
                )

                Text(
                    text = stringResource(R.string.bestmatch_text1),
                    color = colorResource(R.color.orange_2),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.onest_semibold))
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stamp.name,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.onest_semibold))
                        )
                    )

                    Text(
                        text = stamp.attributes.country,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.onest_regular))
                        ),
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }


                Image(
                    painter = painterResource(id = R.drawable.next_button),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(24.dp)
                )
            }
        }

    }
}


@Preview
@Composable
private fun BestMatchPreview() {
    BestMatchContent(
        modifier = Modifier,
        results = emptyList(),
        onBackClick = { }
    )
}

@Preview
@Composable
private fun BestMatchItemPreview() {
    val stamp = StampDataResponse(
        name = "Queen Elizabeth II - Decimal Machin ",
        description = "",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Russia_1918_CPA_1_stamp_%28Hand_with_a_Sword_Splitting_a_Chain_against_a_Rising_Sun%29.jpg/250px-Russia_1918_CPA_1_stamp_%28Hand_with_a_Sword_Splitting_a_Chain_against_a_Rising_Sun%29.jpg",
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
    BestMatchItem(stamp)
}