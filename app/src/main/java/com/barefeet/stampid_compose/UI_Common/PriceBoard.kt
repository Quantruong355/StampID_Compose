package com.barefeet.stampid_compose.UI_Common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.barefeet.stampid_compose.ui.theme.AppTypography
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R

@Composable
fun PriceBoard(
    modifier: Modifier = Modifier,
    currency: String,
    minPrice: Double,
    maxPrice: Double
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp, Alignment.CenterVertically)
        ) {

            Text(
                text = stringResource(R.string.result_text3),
                color = colorResource(R.color.gray_2),
                style = AppTypography.OnestRegular.copy(
                    fontSize = 15.sp,
                )
            )

            Text(
                text = "$currency$minPrice - $currency$maxPrice",
                color = colorResource(R.color.red_1),
                style = AppTypography.OnestSemiBold.copy(
                    fontSize = 18.sp,
                )
            )
        }
    }
}