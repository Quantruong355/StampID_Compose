package com.barefeet.stampid_compose.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.IAPBanner
import com.barefeet.stampid_compose.data.Article
import com.barefeet.stampid_compose.utils.loadArticlesFromAssets

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeVM: HomeViewModel = viewModel(),
    onArticleClick: () -> Unit = {},
    onSettingClick: () -> Unit,
) {
    val uiState by homeVM.uiState.collectAsState()

    HomeContent(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white_2)),
        article_list = uiState.articles,
        onSettingClick = onSettingClick
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    article_list: List<Article>,
    onSettingClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2)),
        contentPadding = PaddingValues(bottom = 170.dp)
    ) {
        item {
            HomeHeader(
                onSettingClick = onSettingClick
            )
        }

        item {
            IAPBanner(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 20.dp),
                onClick = { }
            )
        }

        ArticleSection(article_list, onItemClick = { })


    }
}

@Composable
fun HomeHeader(
    onSettingClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.home_topbar_bg),
                contentScale = ContentScale.FillWidth
            )
            .padding(10.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.home_text1),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.onest_medium))
                )
            )
            Spacer(modifier = Modifier.weight(1f))


                Image(
                    painter = painterResource(id = R.drawable.setting_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = onSettingClick)
                )
        }

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white).copy(alpha = 0.4f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(vertical = 10.dp)
        ){
            Image(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(width = 20.dp, height = 20.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null,
            )
            
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(R.string.home_text2),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.onest_medium))
                )
            )
        }
    }
}

fun LazyListScope.ArticleSection(
    article_list: List<Article>,
    onItemClick: () -> Unit = {}
) {
    item {
        Text(
            text = stringResource(R.string.home_text3),
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
        )
    }
    items(
        items = article_list,
        key = { it.headline!! }
    ){ article ->
        ArticleItem(article= article,
            onClick = onItemClick)
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    val pxValue = with(LocalDensity.current) { 14.dp.toPx() }

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.thumb)
                    .crossfade(true)
                    .transformations(
                        RoundedCornersTransformation(
                            topLeft = pxValue,
                            topRight = pxValue,
                            bottomLeft = pxValue,
                            bottomRight = pxValue
                        )
                    )
                    .build(),
                modifier = Modifier.size(width = 95.dp, height = 100.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(start= 10.dp)
            ){
                Text(
                    text = article.headline!!,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.onest_semibold))
                    ),
                    lineHeight = 20.sp
                )

                Text(
                    text = article.sub_headline!!,
                    color = colorResource(R.color.gray_2),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.onest_regular))
                    ),
                    maxLines = 2,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top= 5.dp)
                )
            }
        }

        HorizontalDivider(
            thickness = 0.5.dp,
            color = colorResource(R.color.white_3),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}


@Preview
@Composable
private fun HomeContentPrev() {
    HomeContent(
        modifier = Modifier,
        article_list = emptyList(),
        onSettingClick = TODO()
    )
}