package com.barefeet.stampid_compose.UI_Common

import android.widget.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barefeet.stampid_compose.R

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.green_2)
        ),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = stringResource(id = R.string.onboard_text7),
            color = colorResource(id = R.color.white),
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.inter_semibold))
            )
        )
    }
}