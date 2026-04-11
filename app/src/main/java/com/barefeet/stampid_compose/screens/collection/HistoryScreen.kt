package com.barefeet.stampid_compose.screens.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.barefeet.stampid_compose.UI_Common.EmptyStampView
import com.barefeet.stampid_compose.UI_Common.RoseThreeLoader

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {

    HistoryContent(modifier = modifier)
}

@Composable
fun HistoryContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.collection_text5,0),
                color = Color.Black,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.onest_semibold))
                ),
                modifier = Modifier.weight(1f)
            )

            SortButton(
                onClick = { }
            )
        }

        EmptyStampView(
            modifier = Modifier.padding(top = 50.dp),
            title = R.string.collection_text10,
            subtitle = R.string.collection_text11,
            titleButton = R.string.collection_text13,
            onClick = { }
        )


    }
}

@Composable
private fun SortButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = colorResource(R.color.gray_3),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.white))
            .clickable { onClick() }
            .padding(vertical = 5.dp, horizontal = 15.dp)

    ) {

        Text(
            text = stringResource(R.string.collection_text7),
            color = colorResource(R.color.black),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular))
            )
        )

        Image(
            painter = painterResource(R.drawable.sort_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.size(20.dp).padding(start = 5.dp)
        )


    }
}

@Preview
@Composable
private fun HistoryPreview() {
    HistoryContent()
}