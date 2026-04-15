package com.barefeet.stampid_compose.screens.camera

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CameraScannerOverlay(
    isStreaming: Boolean
) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                // BẮT BUỘC có graphicsLayer để BlendMode hoạt động
                .graphicsLayer(alpha = 0.99f)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // 1. Vẽ lớp nền mờ (Toàn bộ màn hình)
            drawRect(
                color = Color.Black.copy(alpha = 0.6f),
                size = Size(canvasWidth, canvasHeight)
            )

            // 2. Định nghĩa khung "lỗ" ở giữa (Khung trong suốt)
            val boxWidth = canvasWidth * 0.6f // Rộng 70% màn hình
            val boxHeight = boxWidth * 1.2f // Vuông hoặc chữ nhật tùy ông
            val boxX = (canvasWidth - boxWidth) / 2
            val boxY = (canvasHeight - boxHeight) / 2


            // 3. Vẽ "lỗ" trong suốt (Dùng BlendMode.Clear)
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(boxX, boxY),
                size = Size(boxWidth, boxHeight),
                cornerRadius = CornerRadius(16.dp.toPx()),
                blendMode = BlendMode.Clear // Xóa lớp nền mờ tại đây
            )

            // 4. Vẽ viền trắng cho khung "lỗ"
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(boxX, boxY),
                size = Size(boxWidth, boxHeight),
                cornerRadius = CornerRadius(16.dp.toPx()),
                style = Stroke(width = 2.dp.toPx()) // Vẽ viền
            )
        }
}