package com.barefeet.stampid_compose.UI_Common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.utils.clickableSafe

@Composable
fun StatusImageButton(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {

    val gradientcolor = Brush.linearGradient(
        listOf(colorResource(id = R.color.green_3),colorResource(id = R.color.green_2)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY))

    val inactiveBrush = SolidColor(colorResource(id = R.color.green_1))

    Box(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(if (isActive) gradientcolor  else inactiveBrush)
            .clickableSafe { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = if (isActive) Color.White else colorResource(R.color.gray_2),
                fontFamily = FontFamily(Font(R.font.onest_regular)),
                fontSize = 12.sp
            )
            )
    }
}