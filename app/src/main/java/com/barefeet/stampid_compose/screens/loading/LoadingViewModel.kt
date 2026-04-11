package com.barefeet.stampid_compose.screens.loading

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barefeet.stampid_compose.model.StampDataResponse
import com.barefeet.stampid_compose.network.ApiRepository
import com.barefeet.stampid_compose.utils.CameraManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoadingUiState(
    val isIdentifying: Boolean = false,
    val error: String? = null
)

sealed class LoadingUiEffect {
    data class NavigateToBestMatch(val data: List<StampDataResponse>) : LoadingUiEffect()
    data class ShowToast(val message: String) : LoadingUiEffect()
    object NavigateBack : LoadingUiEffect()
}

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val cameraManager: CameraManager,
    private val apiRepository: ApiRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(LoadingUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LoadingUiEffect>()
    val effect = _effect.asSharedFlow()


    fun identifyStamp(uri: String) {
        if (_uiState.value.isIdentifying) return

        _uiState.update { it.copy(isIdentifying = true) }

        viewModelScope.launch {
            try {
                val response = apiRepository.identifyStamp(uri)

                if (response.response_code == 0) {
                    _effect.emit(LoadingUiEffect.NavigateToBestMatch(response.data))
                } else {
                    _uiState.update { it.copy(error = response.message) }
                    _effect.emit(LoadingUiEffect.ShowToast(response.message))
                    delay(2000)
                    _effect.emit(LoadingUiEffect.NavigateBack)
                }
            } catch (e: Exception) {
                val errorMsg = "Server Error: ${e.message}"
                _uiState.update { it.copy(error = errorMsg) }
                _effect.emit(LoadingUiEffect.ShowToast(errorMsg))

                delay(2000)
                _effect.emit(LoadingUiEffect.NavigateBack)
            } finally {
                _uiState.update { it.copy(isIdentifying = false) }
//                cameraManager.deleteTempFile(Uri.parse(uri))
            }
        }
    }
    }