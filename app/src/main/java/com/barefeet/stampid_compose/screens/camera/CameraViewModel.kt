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
            is CameraUiEvent.OnCloseClick -> {
                sendEffect(CameraUiEffect.CloseScreen)
            }

            is CameraUiEvent.OnCaptureClick -> {
                _uiState.update { it.copy(isCapturing = true) }

                cameraManager.takePhoto(
                    imageCapture = event.imageCapture,
                    onImageSaved = { uri ->
                        _uiState.update { it.copy(isCapturing = false) }
//                        sendEffect(CameraUiEffect.NavigateToLoading(uri))
                        sendEffect(CameraUiEffect.ShowToast("Navigate to loading"))
                    },
                    onError = { error ->
                        _uiState.update { it.copy(isCapturing = false) }
                        sendEffect(CameraUiEffect.ShowToast("Error: ${error.message}"))
                    }
                )

            }
            is CameraUiEvent.OnGalleryClick -> {
                sendEffect(CameraUiEffect.LaunchGalleryPicker)
            }

            is CameraUiEvent.OnSnaptipToggle -> {
                _uiState.update { it.copy(showSnapTip = !it.showSnapTip) }
            }

            is CameraUiEvent.OnImageCaptured -> {
                _uiState.update { it.copy(isCapturing = false) }
                sendEffect(CameraUiEffect.NavigateToLoading(event.uri))
            }
            is CameraUiEvent.OnImagePicked -> {
                sendEffect(CameraUiEffect.NavigateToLoading(event.uri))
            }

            is CameraUiEvent.onCameraError -> {
                _uiState.update { it.copy(isCapturing = false) }
                sendEffect(CameraUiEffect.ShowToast("Error: ${event.message}"))
            }
            else -> {}
        }
    }

    private fun sendEffect(effect: CameraUiEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}