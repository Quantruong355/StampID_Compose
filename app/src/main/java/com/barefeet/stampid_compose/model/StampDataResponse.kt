package com.barefeet.stampid_compose.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StampDataResponse(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("product_price") val productPrice: String,
    @SerializedName("attributes") val attributes: StampAttributes,
    @SerializedName("market_items") val marketItems: List<MarketItem>
)

@Serializable
data class StampAttributes(
    @SerializedName("country") val country: String,
    @SerializedName("issued_on") val issuedOn: String,
    @SerializedName("color") val color: String,
    @SerializedName("face_value") val faceValue: String,
    @SerializedName("series") val series: String,
    @SerializedName("min_price") val minPrice: Double,
    @SerializedName("max_price") val maxPrice: Double,
    @SerializedName("condition") val condition: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("description") val description: String,

)

@Serializable
data class MarketItem(
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("link") val link: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("currency") val currency: String
)