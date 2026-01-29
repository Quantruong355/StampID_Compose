package com.barefeet.stampid_compose.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.IAPBanner
import com.barefeet.stampid_compose.utils.noRippleClickable

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    settingVM: SettingViewModel = viewModel(),
    onBackClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        settingVM.effect.collect { effect ->
            when (effect) {
                is SettingUiEffect.NavigateToBack -> {
                    onBackClick()
                }
                is SettingUiEffect.NavigateToIAP -> {

                }
                is SettingUiEffect.NavigateToMembership -> {

                }
                is SettingUiEffect.NavigateToPrivacy -> {

                }
                is SettingUiEffect.NavigateToTerm -> {

                }
            }
        }
    }

    SettingContent(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_2)),
        onEvent = settingVM::onEvent
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    onEvent: (SettingUiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SettingHeader(onBackClick = { onEvent(SettingUiEvent.OnBackClick) })

        IAPBanner(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 20.dp),
            onClick = { onEvent(SettingUiEvent.OnIAPClick)}
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp)
            ,
            thickness = 0.5.dp,
            color = colorResource(R.color.gray_3),
        )

        MembershipSection(
            modifier = Modifier.padding(start = 10.dp),
            onClick = { onEvent(SettingUiEvent.OnMembershipClick)}
        )

        Legalsection(
            modifier = Modifier.padding(start = 10.dp),
            onPrivacyClick = { onEvent(SettingUiEvent.OnPrivacyClick) },
            onTermClick = { onEvent(SettingUiEvent.OnTermClick) }
        )
    }
}

@Composable
fun SettingHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 15.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .noRippleClickable{onBackClick() }
        )

        Text(
            text = stringResource(R.string.setting_text1),
            color = Color.Black,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun MembershipSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
    ) {
    Text(
        text = stringResource(R.string.setting_text2),
        color = colorResource(R.color.gray_2),
        style = TextStyle(
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.onest_semibold))
        ),
        modifier = modifier.padding(top= 25.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top= 15.dp)
            .fillMaxWidth()
            .noRippleClickable{ onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.diamond_icon),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = modifier) {
            Text(
                text = stringResource(R.string.setting_text3),
                color = Color.Black,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.onest_regular))
                ),
            )

            Text(
                text = stringResource(R.string.setting_text4, "Premium"),
                color = colorResource(R.color.gray_2),
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.onest_regular))
                ),
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 10.dp)
        ,
        thickness = 0.5.dp,
        color = colorResource(R.color.gray_3),
    )
}

@Composable
fun Legalsection(
    modifier: Modifier = Modifier,
    onPrivacyClick: () -> Unit,
    onTermClick: () -> Unit
    ) {

    Text(
        text = stringResource(R.string.setting_text5),
        color = colorResource(R.color.gray_2),
        style = TextStyle(
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(R.font.onest_semibold))
        ),
        modifier = modifier.padding(top= 15.dp)
    )

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(top= 25.dp)
        .noRippleClickable{ onPrivacyClick() }
    ){
        Image(
            painter = painterResource(R.drawable.privacy_icon),
            contentDescription = null,
            modifier = Modifier
        )

        Text(
            text = stringResource(R.string.setting_text6),
            color = Color.Black,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular))
            ),
            modifier = modifier
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top= 30.dp)
            .noRippleClickable{ onTermClick() }
    ){
        Image(
            painter = painterResource(R.drawable.term_icon),
            contentDescription = null,
            modifier = Modifier
        )

        Text(
            text = stringResource(R.string.setting_text7),
            color = Color.Black,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular))
            ),
            modifier = modifier
        )
    }
}
@Preview
@Composable
private fun SettingScreenPrev() {
    SettingScreen(
        onBackClick = {}
    )
}