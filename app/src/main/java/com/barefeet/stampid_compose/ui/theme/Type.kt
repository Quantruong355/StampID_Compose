package com.barefeet.stampid_compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R

// Define Onest FontFamily
val OnestFontFamily = FontFamily(
    Font(R.font.onest_regular, FontWeight.Normal),
    Font(R.font.onest_medium, FontWeight.Medium),
    Font(R.font.onest_semibold, FontWeight.SemiBold),
    Font(R.font.onest_bold, FontWeight.Bold)
)

// Define Inter FontFamily
val InterFontFamily = FontFamily(
    Font(R.font.inter_18pt_regular, FontWeight.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold)
)

// Standard Material 3 Typography
val Typography = Typography(
    // Titles
    titleLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    
    // Body
    bodyLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    // Labels (Often used for buttons or small captions)
    labelLarge = TextStyle(
        fontFamily = OnestFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

/**
 * Custom App Styles for quick access to specific font weights
 * usage: AppTypography.OnestSemiBold
 */
object AppTypography {
    val OnestRegular = TextStyle(fontFamily = OnestFontFamily, fontWeight = FontWeight.Normal)
    val OnestMedium = TextStyle(fontFamily = OnestFontFamily, fontWeight = FontWeight.Medium)
    val OnestSemiBold = TextStyle(fontFamily = OnestFontFamily, fontWeight = FontWeight.SemiBold)
    val OnestBold = TextStyle(fontFamily = OnestFontFamily, fontWeight = FontWeight.Bold)
    
    val InterRegular = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Normal)
    val InterSemiBold = TextStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.SemiBold)
}
