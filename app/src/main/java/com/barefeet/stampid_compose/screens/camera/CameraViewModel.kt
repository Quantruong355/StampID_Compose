package com.barefeet.stampid_compose.screens.camera

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barefeet.stampid_compose.utils.CameraManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraManager: CameraManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<CameraUiEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: CameraUiEvent){
        when(event){
            is CameraUiEvent.OnCloseClick ->    sendEffect(CameraUiEffect.CloseScreen)
            is CameraUiEvent.OnCaptureClick ->  handleCapture(event.imageCapture)
            is CameraUiEvent.OnGalleryClick ->  sendEffect(CameraUiEffect.LaunchGalleryPicker)
            is CameraUiEvent.OnSnaptipToggle -> toggleSnapTip()
            is CameraUiEvent.OnImagePicked ->   handleImagePicked(event.uri)
            is CameraUiEvent.onCameraError ->   handleError(event.message)
            is CameraUiEvent.OnImageCropped ->  handleImageCropped(event.uri)

        }
    }

    private fun handleCapture(imageCapture: ImageCapture) {
        _uiState.update { it.copy(isCapturing = true) }

        cameraManager.takePhoto(
            imageCapture = imageCapture,
            onImageSaved = { uri ->
                _uiState.update { it.copy(isCapturing = false) }
                sendEffect(CameraUiEffect.StartCropImage(uri))
            },
            onError = { error ->
                _uiState.update { it.copy(isCapturing = false) }
                sendEffect(CameraUiEffect.ShowToast("Error: ${error.message}"))
            }
        )
    }

    private fun handleImagePicked(uri: Uri) {
        setLoading(false)
        sendEffect(CameraUiEffect.StartCropImage(uri))
    }

    private fun handleImageCropped(uri: Uri) {
        sendEffect(CameraUiEffect.NavigateToLoading(uri))
    }

    private fun toggleSnapTip() {
        _uiState.update { it.copy(showSnapTip = !it.showSnapTip) }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isCapturing = isLoading) }
    }

    private fun handleError(message: String) {
        setLoading(false)
        sendEffect(CameraUiEffect.ShowToast(message))
    }

    private fun sendEffect(effect: CameraUiEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}