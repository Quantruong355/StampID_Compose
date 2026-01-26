package com.barefeet.stampid_compose.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.barefeet.stampid_compose.R

sealed class OnboardingPage {
    data class Normal(
        @DrawableRes val image: Int,
        @DrawableRes val subImage: Int? = null,
        @StringRes val title: Int,
        @StringRes val description: Int
    ) : OnboardingPage()

    data class RateApp(
        @DrawableRes val image: Int,
        @DrawableRes val subImage: Int? = null,
        @StringRes val title: Int,
        @StringRes val description: Int
    ) : OnboardingPage()
}

object OnboardingPageList {
    val pages = listOf(
        OnboardingPage.Normal(
            R.drawable.onboard1_bg,
            R.drawable.onboard1_img1,
            title = R.string.onboard_text1,
            R.string.onboard_text2
        ),
        OnboardingPage.Normal(
            R.drawable.onboard2_bg,
            null,
            title = R.string.onboard_text3,
            R.string.onboard_text4
        ),
        OnboardingPage.RateApp(
            image = R.drawable.rate_screen_img1,
            subImage = R.drawable.rate_screen_img2,
            title = R.string.onboard_text5,
            R.string.onboard_text6
        )
    )
}