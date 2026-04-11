package com.barefeet.stampid_compose.screens.collection

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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

@Composable
fun AllCollectionScreen(
    modifier: Modifier = Modifier,
    onAddCollection: () -> Unit
) {

    AllCollectionContent(
        modifier = modifier,
        onAddCollection = { onAddCollection() }
    )
}

@Composable
fun AllCollectionContent(
    modifier: Modifier = Modifier,
    onAddCollection: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.collection_text4,0),
                color = Color.Black,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.onest_semibold))
                ),
                modifier = Modifier.weight(1f)
            )

            AddCollectionButton(
                onClick = { onAddCollection() }
            )
        }

        EmptyStampView(
            modifier = Modifier.padding(top = 50.dp),
            title = R.string.collection_text8,
            subtitle = R.string.collection_text9,
            titleButton = R.string.collection_text12,
            onClick = { onAddCollection() }
        )
    }
}

@Composable
private fun AddCollectionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = colorResource(R.color.green_4),
                shape = RoundedCornerShape(8.dp)
            )
            .background(colorResource(R.color.white))
            .clickable { onClick() }
            .padding(vertical = 5.dp, horizontal = 15.dp)

    ) {
        Image(
            painter = painterResource(R.drawable.plus_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.size(20.dp).padding(end = 5.dp)
        )

        Text(
            text = stringResource(R.string.collection_text6),
            color = colorResource(R.color.black),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            )
        )
    }
}


@Preview
@Composable
private fun AllCollectionPreview() {
    AllCollectionContent(
        modifier = Modifier,
        onAddCollection = { }
    )
}