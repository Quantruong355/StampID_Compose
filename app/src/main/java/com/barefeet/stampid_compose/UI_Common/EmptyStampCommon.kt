package com.barefeet.stampid_compose.UI_Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.barefeet.stampid_compose.ui.theme.AppTypography
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.utils.noRippleClickable

@Composable
fun EmptyStampView(
    modifier: Modifier = Modifier,
    title: Int,
    subtitle: Int,
    titleButton: Int,
    onClick : () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.empty_stamp_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
        )

        Text(
            text = stringResource(title),
            color = Color.Black,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            ),
            modifier = Modifier.padding(top = 5.dp)
        )

        Text(
            text = stringResource(subtitle),
            color = colorResource(R.color.gray_2),
            style = TextStyle(
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular))
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(R.color.green_2))
                .clickable { onClick() }
                .padding(vertical = 8.dp, horizontal = 15.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.plus_icon),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(20.dp).padding(end = 5.dp)
            )

            Text(
                text = stringResource(titleButton),
                color = colorResource(R.color.white),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semibold))
                )
            )
        }

    }
}

@Preview
@Composable
private fun EmptyStampPreview() {
    EmptyStampView(
        modifier = Modifier,
        title = R.string.collection_text8,
        subtitle = R.string.collection_text9,
        titleButton = R.string.collection_text12,
        onClick = { }
    )
}