package com.barefeet.stampid_compose.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun clearOldCache(context: Context) {
    val cacheFiles = context.cacheDir.listFiles()
    val currentTime = System.currentTimeMillis()
    cacheFiles?.forEach { file ->
        // Delete if old file exist > 2 day
//        if (currentTime - file.lastModified() > 2 * 24 * 60 * 60 * 1000) {
            file.delete()
//        }
    }
}

fun saveImageToInternalStorage(context: Context, tempUri: Uri): String? {
    return try {
        val fileName = "STAMP_${System.currentTimeMillis()}.jpg"

        // 2. Tạo file đích trong thư mục filesDir/stamps
        val directory = File(context.filesDir, "stamps")
        if (!directory.exists()) directory.mkdirs() // Tạo thư mục nếu chưa có

        val destFile = File(directory, fileName)

        // 3. Copy dữ liệu từ file tạm sang file đích
        context.contentResolver.openInputStream(tempUri)?.use { input ->
            destFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        fileName
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}