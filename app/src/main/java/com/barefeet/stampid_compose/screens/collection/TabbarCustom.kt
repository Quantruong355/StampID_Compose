package com.barefeet.stampid_compose.screens.collection

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.ui.theme.AppTypography

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabbarCustom(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {
    val density = LocalDensity.current
    val tabCount = items.size

    BoxWithConstraints(
        modifier = modifier
            .padding(top = 15.dp)
            .width(180.dp)
            .height(35.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.gray_5))
            .padding(3.dp)
    ) {
        if (items.isNotEmpty()) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / tabCount

            // 1. Tính toán Offset theo Px để dùng cho Canvas (Mượt theo ngons tay)
            val indicatorOffsetPx by remember(pagerState, maxWidth) {
                derivedStateOf {
                    val position = pagerState.currentPage + pagerState.currentPageOffsetFraction
                    val maxWidthPx = with(density) { maxWidth.toPx() }
                    val tabWidthPx = maxWidthPx / tabCount
                    position * tabWidthPx
                }
            }

            // 2. Chuyển sang Dp để dùng cho Modifier.offset (Shadow)
            // Dùng IntOffset trong offset lambda để tối ưu Recomposition
            val indicatorOffsetDp = with(density) { indicatorOffsetPx.toDp() }

            // Lớp bóng đổ (Shadow) đi theo viên thuốc
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffsetDp)
                    .width(tabWidth)
                    .fillMaxHeight()
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .background(Color.Transparent) // Chỉ lấy shadow
            )

            // 3. Lớp nội dung dùng DrawLayer để xử lý BlendMode (Màu chữ)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        val tabWidthPx = size.width / tabCount
                        val currentOffsetPx = indicatorOffsetPx

                        // Vẽ nền đen giả định (để tạo lớp Mask cho chữ)
                        val textPadding = 8.dp.toPx()
                        drawRoundRect(
                            color = Color.Black,
                            topLeft = Offset(currentOffsetPx + textPadding, textPadding),
                            size = Size(tabWidthPx - textPadding * 1, size.height - textPadding * 1),
                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                        )

                        // Kỹ thuật Draw Layer để đè màu trắng lên chữ xám
                        drawWithLayer {
                            drawContent() // Vẽ chữ Xám (mặc định của Text)

                            // Vẽ viên thuốc Trắng đè lên.
                            // BlendMode.SrcOut sẽ làm phần chữ xám bên dưới biến mất và hiện màu trắng/đen tùy logic
                            drawRoundRect(
                                color = Color.White,
                                topLeft = Offset(currentOffsetPx, 0f),
                                size = Size(tabWidthPx, size.height),
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                                blendMode = BlendMode.SrcOut
                            )
                        }
                    }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .weight(1f) // Dùng weight để tự động chia đều
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { onSelectionChange(index) }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            color = Color.Gray,
                            style = AppTypography.OnestRegular.copy(
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

// Source - https://stackoverflow.com/a/77333934
// Posted by Thracian
// Retrieved 2026-04-08, License - CC BY-SA 4.0

@Preview
@Composable
private fun TextSwitchTest() {
    val items = remember {
        listOf("All", "History")
    }

    var selectedIndex by remember {
        mutableStateOf(0)
    }


//    Column {
//        TabbarCustom(
//            selectedIndex = selectedIndex,
//            items = items,
//            onSelectionChange = {
//                selectedIndex = it
//            }
//        )
//    }
}

