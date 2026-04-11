package com.barefeet.stampid_compose.screens.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.NewCollectionDialog
import kotlinx.coroutines.launch

@Composable
fun CollectionScreen(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    Box(){
        CollectionContent(
            modifier = modifier,
            onAddCollection = { showDialog = true }

        )

        if (showDialog) {
            NewCollectionDialog(
                onDismiss = { showDialog = false },
                onSave = { name ->
                    println("Đã lưu: $name")
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun CollectionContent(
    modifier: Modifier = Modifier,
    onAddCollection: () -> Unit
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.white))
        ){
            Text(
                text = stringResource(R.string.collection_text1),
                color = Color.Black,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.onest_semibold))
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp)
                    .padding(bottom = 10.dp)
            )
        }

        StatisticBoard()
        CollectionBody(
            onAddCollection = { onAddCollection() }
        )
    }
}

@Composable
fun StatisticBoard() {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .padding(horizontal = 15.dp)
    ){
        Image(
            painter = painterResource(R.drawable.collection_statistic_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.Fit
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "05",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.onest_semibold))
                    )
                )

                Text(
                    text = stringResource(R.string.collection_text2),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.onest_regular))
                    )
                )
            }

            VerticalDivider(
                modifier = Modifier.height(70.dp),
                thickness = 1.dp,
                color = Color.White
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "05",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.onest_semibold))
                    )
                )

                Text(
                    text = stringResource(R.string.collection_text3),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.onest_regular))
                    )
                )
            }
        }
    }
}

@Composable
fun CollectionBody(
    modifier: Modifier = Modifier,
    onAddCollection: () -> Unit
) {
    val tabs = listOf("All", "History")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    TabbarCustom(
        modifier = modifier,
        pagerState = pagerState,
        items = tabs,
        onSelectionChange = { index ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }
        }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .padding(top = 20.dp),
    ) { page ->
        when (page) {
            0 -> AllCollectionScreen(
                onAddCollection = { onAddCollection() }
            )
            1 -> HistoryScreen()
        }
    }
}

@Preview
@Composable
private fun CollectionScreenPrev() {
    CollectionContent(
        onAddCollection = {}
    )
}