package com.barefeet.stampid_compose.UI_Common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
fun IAPBanner(
    modifier: Modifier,
    onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.iap_banner_bg),
                contentScale = ContentScale.FillWidth
            )
            .clickable(
                onClick = onClick
            )
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.iap_text1),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.onest_semibold))

                )
            )

            Text(
                text = stringResource(R.string.iap_text2),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.onest_regular))

                )
            )

            Text(
                text = stringResource(R.string.iap_text3),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.onest_regular))

                ),
                modifier = Modifier.background(
                    color = colorResource(id = R.color.orange_1),
                    shape = RoundedCornerShape(8.dp)
                )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )

        }
    }
}

@Preview
@Composable
private fun IAPBannerPrev() {
    IAPBanner(
        modifier = Modifier,
        onClick = {}
    )
}