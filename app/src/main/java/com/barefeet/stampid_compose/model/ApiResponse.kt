package com.barefeet.stampid_compose.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("cache_id") val cacheId: String,
    @SerializedName("error_code") val response_code: Int,
    @SerializedName("error_message") val message: String,
    @SerializedName("data") val data: List<StampDataResponse>
)
