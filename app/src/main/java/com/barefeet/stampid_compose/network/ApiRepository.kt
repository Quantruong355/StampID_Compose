package com.barefeet.stampid_compose.network

import android.content.Context
import android.net.Uri
import android.util.Log
import com.barefeet.stampid_compose.model.ApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {

    suspend fun identifyStamp(uri: String): ApiResponse {
        val filePart = uriToMultipart(uri)
            ?: throw Exception("Could not read image file")

        return apiService.identifyStamp(filePart)
    }


    private fun uriToMultipart(uriString: String): MultipartBody.Part? {
        return try {
            val uri = Uri.parse(uriString)

            // 2. Lấy cái 'path' thực sự (nó sẽ bỏ cái file:// đi)
            // Kết quả sẽ là: /data/user/0/com.barefeet.stampid_compose/cache/CROP_...jpg
            val actualPath = uri.path ?: return null
            val file = File(actualPath)

            Log.d("ApiRepository", "Path sau khi parse: $actualPath")
            Log.d("ApiRepository", "File có tồn tại không: ${file.exists()}")

            if (!file.exists()) return null

            file.inputStream().use { stream ->
                val bytes = stream.readBytes()
                val requestFile = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull())

                MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile
                )
            }
        } catch (e: Exception) {
            Log.e("ApiRepository", "Lỗi: ${e.message}")
            null
        }
    }
}