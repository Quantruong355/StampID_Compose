package com.barefeet.stampid_compose.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.ui.theme.AppTypography
import com.barefeet.stampid_compose.utils.clickableSafe
import com.barefeet.stampid_compose.utils.noRippleClickable

@Composable
fun SnapTipView(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest =  onDismiss ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.white),
                contentColor = colorResource(R.color.white)
            )
        ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(20.dp)

                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)){
                        Text(
                            text = stringResource(R.string.camera_text2),
                            modifier = Modifier
                                .align(Alignment.Center),
                            color = colorResource(R.color.black),
                            style = MaterialTheme.typography.titleMedium.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.close_icon4),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clickableSafe { onDismiss() }
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.snaptip_img1),
                        contentDescription = null,
                        modifier.size(width = 123.dp, height = 118.dp)
                    )

                    SnaptipItem(
                        img = R.drawable.snaptip_img2,
                        text_regular = stringResource(R.string.snaptip_text1),
                        text_bold = stringResource(R.string.snaptip_text2)
                    )

                    SnaptipItem(
                        img = R.drawable.snaptip_img3,
                        text_regular = stringResource(R.string.snaptip_text3),
                        text_bold = stringResource(R.string.snaptip_text4)
                    )

                    SnaptipItem(
                        img = R.drawable.snaptip_img4,
                        text_regular = stringResource(R.string.snaptip_text3),
                        text_bold = stringResource(R.string.snaptip_text6)
                    )

                    SnaptipItem(
                        img = R.drawable.snaptip_img5,
                        text_regular = stringResource(R.string.snaptip_text7),
                        text_bold = stringResource(R.string.snaptip_text8)
                    )

                    Text(
                        text = stringResource(R.string.snaptip_text9),
                        modifier = Modifier.padding(top = 15.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.gray_2),
                        style = AppTypography.OnestRegular.copy(
                            fontSize = 13.sp,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                }
        }
    }
}

@Composable
fun SnaptipItem(
    img: Int,
    text_regular: String,
    text_bold: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = null,
            modifier = Modifier.size(width = 70.dp, height = 67.dp)
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 13.sp,
                        fontFamily = AppTypography.OnestRegular.fontFamily,
                        color = Color.Black
                    )
                ) {
                    append(text_regular)
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 13.sp,
                        fontFamily = AppTypography.OnestBold.fontFamily,
                        color = Color.Black
                    )
                ) {
                    append(" " + text_bold)
                }
            },
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}