package com.barefeet.stampid_compose.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    HomeContent(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white_2))
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            HomeHeader()
        }

//        item {
//            BannerSection()
//        }

//        val products = listOf("A", "B", "C")
//        items(products) { product ->
//            ProductItem(product)
//        }
    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
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



@Preview
@Composable
private fun HomeContentPrev() {
    HomeContent()
}