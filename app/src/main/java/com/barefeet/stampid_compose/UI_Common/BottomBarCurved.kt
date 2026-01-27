package com.barefeet.stampid_compose.UI_Common

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


class BottomBarCurved : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val width = size.width
            val height = size.height

            // 1. Tách riêng độ rộng và độ sâu
            // Mẹo: Để code gọn, dùng with(density) để đổi dp sang px
            with(density) {
                val cutoutRadius = 42.dp.toPx() // Độ rộng của miệng lỗ (bán kính)
                val cutoutDepth = 47.dp.toPx()  // <--- TĂNG CÁI NÀY ĐỂ SÂU HƠN (Lớn hơn 42dp)
                val curveRadius = 30.dp.toPx()  // Độ bo cong mượt ở mép trên (giảm chút cho gọn)
                val cutoutCenter = width / 2f

                moveTo(0f, 0f)

                // --- VẼ ĐƯỜNG CONG ---

                // Điểm bắt đầu uốn cong (Mép trái)
                val startCurve = cutoutCenter - cutoutRadius - curveRadius
                lineTo(startCurve, 0f)

                // Curve đi xuống (Nửa bên trái)
                // Logic: Uốn từ mép trên (0) xuống đáy (cutoutDepth)
                cubicTo(
                    x1 = cutoutCenter - cutoutRadius, y1 = 0f,            // Control point 1: Giữ đường ngang
                    x2 = cutoutCenter - cutoutRadius, y2 = cutoutDepth,   // Control point 2: Kéo thẳng xuống độ sâu mới
                    x3 = cutoutCenter, y3 = cutoutDepth                   // Điểm đáy: Chính giữa và sâu nhất
                )

                // Curve đi lên (Nửa bên phải)
                // Logic: Uốn từ đáy (cutoutDepth) lên mép trên (0)
                cubicTo(
                    x1 = cutoutCenter + cutoutRadius, y1 = cutoutDepth,   // Control point 1: Từ độ sâu kéo lên
                    x2 = cutoutCenter + cutoutRadius, y2 = 0f,            // Control point 2: Về lại mặt phẳng ngang
                    x3 = cutoutCenter + cutoutRadius + curveRadius, y3 = 0f // Điểm kết thúc curve
                )

                // --- KẾT THÚC ---
                lineTo(width, 0f)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
        })
    }
}

// Hàm hỗ trợ đổi dp sang px
fun Dp.toPx(density: Density) = with(density) { this@toPx.toPx() }