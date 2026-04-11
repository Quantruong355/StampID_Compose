package com.barefeet.stampid_compose.utils

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.barefeet.stampid_compose.data.Article
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.InputStreamReader

fun loadArticlesFromAssets(context: Context): List<Article>? {
    val inputStream = context.assets.open("Articles.json")
    val reader = InputStreamReader(inputStream)

    val listType = object : TypeToken<List<Article>>() {}.type

    return Gson().fromJson(reader, listType)
}

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
)
: Modifier = composed {
    this.clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

fun Modifier.clickableSafe(
    enabled: Boolean = true,
    showRipple: Boolean = false,
    onClick: () -> Unit
): Modifier = composed {

    val lastClickTime = remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = if (showRipple) ripple() else null
    ) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime.longValue > 500L) {
            onClick()
            lastClickTime.longValue = currentTime
        }
    }
}