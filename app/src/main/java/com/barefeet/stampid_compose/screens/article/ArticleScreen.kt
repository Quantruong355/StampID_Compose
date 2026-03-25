package com.barefeet.stampid_compose.screens.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.CommonButton
import com.barefeet.stampid_compose.data.Article
import com.barefeet.stampid_compose.screens.home.ArticleItem
import com.barefeet.stampid_compose.screens.iap.HeadDiamondSection
import com.barefeet.stampid_compose.screens.iap.IAPFeatures
import com.barefeet.stampid_compose.screens.iap.IAPPriceBoard
import com.barefeet.stampid_compose.utils.noRippleClickable

@Composable
fun ArticleScreen(
    article: Article,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ArticleContent(
        article = article,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun ArticleContent(
    article: Article,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize().background(colorResource(R.color.white))){
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = article.thumb,
                contentDescription = "Image by ${article.headline}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(294.dp),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.LightGray),
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        ArticleContentBody(article= article)

        Row(){
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(end = 20.dp)
                    .noRippleClickable {
                        onBackClick()
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_icon2),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun ArticleContentBody(
    article: Article,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    )
    {
        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ){}
        }

        item{
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .background(colorResource(R.color.white))
                    .padding(horizontal = 10.dp)
                    .padding(top = 20.dp)
                    .padding(bottom = 20.dp)
            ){

                Text(
                    text = article.headline,
                    color = colorResource(id = R.color.black),
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.onest_semibold)),
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = article.sub_headline,
                    color = colorResource(id = R.color.black),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.onest_regular)),
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )

                article.sections?.forEach { section ->
                    ArticleDetailItem(
                        section = section
                    )
                }
            }
        }
    }
}

fun LazyListScope.ArticleDetailSection(
    section_list: List<Article.Section>,
    onItemClick: (Int) -> Unit = {}
) {

    items(
        items = section_list
    ) { section ->
        Text(text = section.title ?: "")
    }
}

@Composable
fun ArticleDetailItem(
    section: Article.Section,
) {
    Text(
        text = section.title ?: "",
        color = colorResource(id = R.color.black),
        style = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.onest_semibold)),
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        modifier = Modifier.padding(top = 15.dp)
    )

    if(!section.image.isNullOrEmpty()){
        AsyncImage(
            model = section.image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(294.dp)
                .padding(vertical = 20.dp),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.LightGray),
        )
    }

    Text(
        text = section.content ?: "",
        color = colorResource(id = R.color.black),
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = FontFamily(Font(R.font.onest_regular)),
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
        ),
        modifier = Modifier.padding(top = 5.dp)
    )


}



@Preview
@Composable
private fun ArticleContent() {
    ArticleContent(
        article = Article("Headline", emptyList(),
            "Sub Headline",
            "https://www.thesprucecrafts.com/thmb/1dXMO6crkv8a5Ez8Peb0aO7Mxzk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/close-up-of-stamps-collection-and-magnifying-glass-116359212-5a8e23cefa6bcc003708ff62.jpg"),
        onBackClick = {},
        modifier = Modifier)
}