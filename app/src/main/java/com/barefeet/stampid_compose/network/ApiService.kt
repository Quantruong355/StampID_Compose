package com.barefeet.stampid_compose.network

import com.barefeet.stampid_compose.model.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/detectstamp")
    suspend fun identifyStamp(
        @Part filePart: MultipartBody.Part
    ): ApiResponse
}