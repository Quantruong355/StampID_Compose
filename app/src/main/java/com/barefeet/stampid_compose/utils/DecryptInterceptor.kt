package com.barefeet.stampid_compose.utils

import android.os.Build
import android.security.keystore.KeyProperties
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class DecryptInterceptor(private val key: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain
        .run { proceed(request()) }
        .let { response ->
            return@let if (response.isSuccessful) {
                val body = response.body!!

                val contentType = body.contentType()
                val charset = contentType?.charset() ?: Charset.defaultCharset()
                val buffer = body.source().apply { request(Long.MAX_VALUE) }.buffer
                val bodyContent = buffer.clone().readString(charset)

                response.newBuilder()
                    .body(bodyContent.let(::decryptBody).toResponseBody(contentType))
                    .build()
            } else response
        }

    private fun decryptBody(content: String): String {
        val decryptor = AESDecryptor(key)
        val removePrefix = content.substring(1)
        return decryptor.decrypt(removePrefix)
    }
}

class AESDecryptor(secretKeyString: String) {
    private val secretKey: SecretKey

    init {
        secretKey = SecretKeySpec(
            secretKeyString.toByteArray(Charsets.UTF_8),
            0,
            secretKeyString.toByteArray(Charsets.UTF_8).size,
            ALGORITHM
        )
    }

    fun decrypt(encryptedText: String): String {
        try {
            // Lay 16 ky tu dau Lam IV
            val ivString = encryptedText.substring(0, 16)
            val iv = ivString.toByteArray(Charsets.UTF_8)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            // Decode Base64 phan con lai
            val encryptedData = encryptedText.substring(16)
            val encryptedBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getMimeDecoder().decode(encryptedData)
            } else {
                android.util.Base64.decode(encryptedData, android.util.Base64.URL_SAFE)
            }
            //Giai ma
            cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                IvParameterSpec(iv)
            )
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            var str = String(decryptedBytes, Charsets.UTF_8)
            str = str.trim()
            val lastIndexOfChar = str.lastIndexOf('}')
            if (lastIndexOfChar != -1) {
                str = str.substring(0, lastIndexOfChar + 1)
            }
            return str
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}