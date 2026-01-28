package com.barefeet.stampid_compose.data

import android.os.Parcelable
import androidx.compose.runtime.Immutable

@Immutable
data class Article(
//    @SerializedName("headline")
    val headline: String?,
//    @SerializedName("sections")
    val sections: List<Section>?,
//    @SerializedName("sub_headline")
    val sub_headline: String?,
//    @SerializedName("thumb")
    val thumb: String?
) {
    data class Section(
//        @SerializedName("content")
        val content: String?,
//        @SerializedName("image")
        val image: String?,
//        @SerializedName("title")
        val title: String?
    )
}
