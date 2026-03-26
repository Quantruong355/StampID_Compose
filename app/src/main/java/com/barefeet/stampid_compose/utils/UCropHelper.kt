package com.barefeet.stampid_compose.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.yalantis.ucrop.UCrop

fun startCrop(
    sourceUri: Uri,
    destinationUri: Uri,
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val options = UCrop.Options().apply {
        setCompressionFormat(Bitmap.CompressFormat.JPEG)
        setCompressionQuality(90)
        setHideBottomControls(false)
        setFreeStyleCropEnabled(true)
    }

    val uCropIntent = UCrop.of(sourceUri, destinationUri)
        .withOptions(options)
        .getIntent(context)

    launcher.launch(uCropIntent)
}