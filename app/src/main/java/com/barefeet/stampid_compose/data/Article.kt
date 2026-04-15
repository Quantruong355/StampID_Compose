package com.barefeet.stampid_compose.data

import android.os.Parcelable
import androidx.compose.runtime.Immutable

@Immutable
data class Article(
    val headline: String,
    val sections: List<Section>,
    val sub_headline: String,
    val thumb: String?
) {
    data class Section(
        val content: String?,
        val image: String?,
        val title: String?
    )
}
