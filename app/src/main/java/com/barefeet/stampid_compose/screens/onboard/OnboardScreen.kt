package com.barefeet.stampid_compose.screens.onboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.UI_Common.CommonButton
@Composable
fun OnboardScreen(
    modifier: Modifier = Modifier,
    onboardVM : OnboardViewModel = hiltViewModel(),
    onFinish: () -> Unit
    ) {

    val uiState by onboardVM.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = { uiState.pages.size})
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.currentPage) {
        if(pagerState.currentPage != uiState.currentPage){
            pagerState.animateScrollToPage(uiState.currentPage)
        }
    }

    LaunchedEffect(Unit) {
        onboardVM.navigateToHome.collect {
            onFinish()
        }
    }

    OnboardContent(
        modifier = modifier,
        pagerState = pagerState,
        onNextClick = { onboardVM.onEvent(OnboardUiEvent.OnNextClick)}
    )
}

@Composable
fun OnboardContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onNextClick: () -> Unit
) {
    Box(modifier = modifier){
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            OnboardPageItem(
                page = OnboardingPageList.pages[pageIndex]
            )
        }

        if(pagerState.currentPage != OnboardingPageList.pages.size - 1){
            OnboardBottom(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .padding(horizontal = 15.dp),
                page = OnboardingPageList.pages[pagerState.currentPage] as OnboardingPage.Normal,
                onClick = onNextClick
            )
        }else{
           Column(
               modifier = Modifier
                   .align(Alignment.BottomCenter)
                   .padding(bottom = 20.dp)
                   .padding(horizontal = 15.dp)
           ) {
               CommonButton(onClick = onNextClick)
           }
        }

    }
}

@Composable
fun OnboardPageItem(
    page: OnboardingPage
) {
    when(page){
        is OnboardingPage.Normal -> NormalOnBoardLayout(page = page)

        else -> RateAppLayout(page = page as OnboardingPage.RateApp)
    }
}

@Composable
fun NormalOnBoardLayout(
    page: OnboardingPage.Normal
) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        page.subImage?.let { subImg ->
            Image(
                painter = painterResource(id = subImg),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(302.dp, 492.dp)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun RateAppLayout(
    page: OnboardingPage.RateApp
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.rate_screen_img1),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 40.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = page.title),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.onest_semibold))
            )
        )

        Text(
            text = stringResource(id = page.description),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular))
            )
        )

        Image(
            painter = painterResource(id = page.subImage),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 30.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun OnboardBottom(
    modifier: Modifier = Modifier,
    page: OnboardingPage.Normal,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier
            .background(
                color = colorResource(id = R.color.green_1),
                shape = RoundedCornerShape(40.dp)
            )
            .padding(15.dp)
    ){
        AnimatedContent(
            targetState = page,
            label = "textAnimation"
        ) { targetPage ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = targetPage.title),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontFamily = FontFamily(Font(R.font.onest_bold))
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = targetPage.description),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.onest_regular))
                    )
                )
            }
        }

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.green_2)
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(id = R.string.onboard_text0),
                color = colorResource(id = R.color.white),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.onest_semibold))
                )
            )
        }
    }
}

@Preview
@Composable
private fun OnboardContentPreview() {
    OnboardContent(
        pagerState = rememberPagerState(pageCount = { OnboardingPageList.pages.size }),
        modifier = Modifier,
        onNextClick = {}
    )
}

@Preview
@Composable
private fun RateAppLayoutPreview() {
    RateAppLayout(
        page = OnboardingPage.RateApp(
            image = R.drawable.rate_screen_img1,
            subImage = R.drawable.rate_screen_img2,
            title = R.string.onboard_text5,
            R.string.onboard_text6
        )
    )
}

