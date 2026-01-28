package com.barefeet.stampid_compose.utils

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
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

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}