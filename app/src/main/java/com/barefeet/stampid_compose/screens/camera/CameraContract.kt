package com.barefeet.stampid_compose.screens.camera

import android.net.Uri
import androidx.camera.core.ImageCapture

data class CameraUiState(
    val selectedImageUri : Uri? = null,
    val isCapturing: Boolean = false,
    val isCameraReady: Boolean = false,
    val showSnapTip: Boolean = false,
    val errorMessage: String? = null
)

sealed class CameraUiEvent {
    object OnCloseClick : CameraUiEvent()
    data class OnCaptureClick(val imageCapture: ImageCapture) : CameraUiEvent()
    object OnGalleryClick : CameraUiEvent()
    object OnSnaptipToggle : CameraUiEvent()

    data class OnImageCaptured(val uri: Uri) : CameraUiEvent()
    data class OnImagePicked(val uri: Uri) : CameraUiEvent()
    data class onCameraError(val message: String) : CameraUiEvent()
}

sealed class CameraUiEffect {
    object CloseScreen : CameraUiEffect()
    object LaunchGalleryPicker : CameraUiEffect()
    data class NavigateToLoading(val uri: Uri) : CameraUiEffect()
    data class ShowToast(val message: String) : CameraUiEffect()
}