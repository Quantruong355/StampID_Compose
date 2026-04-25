package com.barefeet.stampid_compose.screens.iap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.CommonButton
import com.barefeet.stampid_compose.ui.theme.AppTypography
import com.barefeet.stampid_compose.utils.clickableSafe

@Composable
fun IAPScreen(
    iapVM: IAPViewModel = viewModel(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iapState by iapVM.uiState.collectAsState()

    IAPContent(
        modifier = modifier,
        iapState = iapState,
        onPlanSelected = { plan -> iapVM.onPlanSelected(plan) },
        onBackClick = onBackClick
    )
}

@Composable
fun IAPContent(
    iapState: IAPState,
    onPlanSelected: (IAPPlan) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.iap_img),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        IAPContentBody(
            iapState = iapState,
            onPlanSelected = onPlanSelected,
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp)
                .padding(top = 30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.close_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickableSafe{ onBackClick() }
            )}
    }
}

@Composable
fun IAPContentBody(
    iapState: IAPState,
    onPlanSelected: (IAPPlan) -> Unit,
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
                horizontalAlignment = Alignment.CenterHorizontally,
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
                    .padding(bottom = 100.dp)
            ){

                HeadDiamondSection()

                IAPFeatures(text = stringResource(R.string.iap_text5),modifier = Modifier.align(Alignment.Start))
                IAPFeatures(text = stringResource(R.string.iap_text6),modifier = Modifier.align(Alignment.Start))
                IAPFeatures(text = stringResource(R.string.iap_text7),modifier = Modifier.align(Alignment.Start))
                IAPFeatures(text = stringResource(R.string.iap_text8),modifier = Modifier.align(Alignment.Start))

                iapState.packages.forEach { pkg ->
                    IAPPriceBoard(
                        item = pkg,
                        isSelected = iapState.selectedPlan == pkg.plan,
                        onPlanSelected = onPlanSelected
                    )
                }

                CommonButton(
                    title = stringResource(R.string.onboard_text0),
                    modifier = Modifier.padding(top = 20.dp)
                ) { }

                Text(
                    text = stringResource(R.string.iap_text15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    color = colorResource(R.color.gray_2),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodySmall.copy(
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
fun HeadDiamondSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 10.dp)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.diamond_icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = stringResource(R.string.iap_text4),
            color = colorResource(id = R.color.black),
            style = AppTypography.OnestSemiBold.copy(
                fontSize = 20.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.diamond_icon),
            contentDescription = null,
        )
    }
}

@Composable
fun IAPFeatures(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 2.dp)

    ){
        Image(
            painter = painterResource(id = R.drawable.tick_icon),
            contentDescription = null,
        )

        Text(
            text = text,
            color = colorResource(id = R.color.black),
            style = AppTypography.OnestMedium.copy(
                fontSize = 13.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )

    }
}

@Composable
fun IAPPriceBoard(
    item: SubscriptionPackage,
    isSelected: Boolean,
    onPlanSelected: (IAPPlan) -> Unit,
) {

    val cornerShape = RoundedCornerShape(14.dp)
    val borderColor = if (isSelected) colorResource(R.color.green_2) else colorResource(R.color.gray_4)
    val selectIcon = if(isSelected) R.drawable.checkbox_selected else R.drawable.checkbox_unselected

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 10.dp)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = cornerShape
            )
            .clip(cornerShape)
            .background(Color.White)
            .padding(horizontal = 10.dp)
            .clickableSafe{ onPlanSelected(item.plan) },

        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                painter = painterResource(id = selectIcon),
                contentDescription = null
            )

            Text(
                text = item.title,
                color = colorResource(id = R.color.black),
                style = AppTypography.OnestSemiBold.copy(
                    fontSize = 14.sp,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(){
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            fontFamily = AppTypography.OnestBold.fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    ) {
                        append(item.price)
                    }

                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = AppTypography.OnestRegular.fontFamily,
                            color = Color.Black
                        )
                    ) {
                        append(item.unit)
                    }
                }
            )

            if(item.pricePerWeek != null){
                Text(
                    text = "Just ${item.pricePerWeek}/week",
                    color = colorResource(R.color.gray_2),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun IAPContentPreview() {
    IAPContent(
        modifier = Modifier.fillMaxSize(),
        iapState = IAPState(),
        onPlanSelected = {},
        onBackClick = {}
    )
}
