package com.barefeet.stampid_compose.screens.loading

import android.net.Uri
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import com.barefeet.stampid_compose.ui.theme.AppTypography
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.model.StampDataResponse

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    imageUri: String,
    loadingVM: LoadingViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToBestMatch: (List<StampDataResponse>) -> Unit
    ) {

    val uiState by loadingVM.uiState.collectAsStateWithLifecycle()
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        loadingVM.identifyStamp(imageUri)
    }

    LaunchedEffect(Unit) {
        loadingVM.effect.collect { effect ->
            when (effect) {
                is LoadingUiEffect.ShowToast -> {
                    Toast.makeText(ctx, effect.message, Toast.LENGTH_SHORT).show()
                }

                is LoadingUiEffect.NavigateToBestMatch -> {
                    onNavigateToBestMatch(effect.data)
                }

                is LoadingUiEffect.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    BackHandler(enabled = true) { }

    LoadingContent(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white_2))
    )
}

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
        ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ScanningView()
            LoadingTextsView()
        }
    }

}

@Composable
fun ScanningView(modifier: Modifier = Modifier) {

    val infiniteTransition = rememberInfiniteTransition(label = "Scanning")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Phase"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.frame_img),
            contentDescription = null,
            modifier = Modifier.size(width = 209.dp, height = 191.dp)
        )

        Image(
            painter = painterResource(R.drawable.stamp_loading),
            contentDescription = null,
            modifier = Modifier.size(width = 122.dp, height = 152.dp)
        )

        Image(
            painter = painterResource(R.drawable.scan_bar),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .graphicsLayer {
                    val isGoingDown = phase <= 1f
                    val currentPosition = if (isGoingDown) phase else (2f - phase)

                    // Lật bitmap: Đi xuống thì 1 (bình thường), đi lên thì -1 (lộn ngược)
                    scaleY = if (isGoingDown) 1f else -1f

                    // 3. FIX LỖI TRANSLATION Y:
                    // size.height ở đây là chiều cao của cái scan_bar, không phải của cái Box!
                    // M muốn nó chạy dọc theo cái frame 191.dp thì phải truyền 191.dp vào.
                    // Trừ đi chiều cao của thanh scan để nó không bị lọt thỏm ra ngoài khung
                    val maxTravelDistance = 191.dp.toPx() - size.height

                    translationY = currentPosition * maxTravelDistance
                }
        )

    }
}

@Composable
fun LoadingTextsView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(top = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = colorResource(R.color.black),
            strokeWidth = 2.dp
        )

        Text(
            text = stringResource(R.string.loading_text1),
            modifier = Modifier.padding(start = 10.dp),
            color = colorResource(R.color.black),
            style = AppTypography.OnestSemiBold.copy(
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }

    Text(
        text = stringResource(R.string.loading_text2),
        modifier = Modifier.padding(top = 10.dp),
        color = colorResource(R.color.gray_2),
        style = AppTypography.OnestRegular.copy(
            fontSize = 13.sp,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
    )

}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingContent(
        modifier = Modifier
    )
}

@Preview
@Composable
private fun ScanningViewPreview() {
    ScanningView(
        modifier = Modifier
    )
}