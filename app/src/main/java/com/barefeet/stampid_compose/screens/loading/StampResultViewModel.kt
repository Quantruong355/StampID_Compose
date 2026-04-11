package com.barefeet.stampid_compose.screens.loading

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.barefeet.stampid_compose.model.StampDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class StampResultViewModel @Inject constructor() : ViewModel() {

    var userImageUri by mutableStateOf<Uri?>(null)
        private set

    private val _scanResult = MutableStateFlow<List<StampDataResponse>>(emptyList())
    val scanResult = _scanResult.asStateFlow()

    fun updateResult(data: List<StampDataResponse>) {
        _scanResult.value = data
    }

    fun updateCapturedImage(uri: Uri) {
        userImageUri = uri
    }

    fun clearData() {
        _scanResult.value = emptyList()
        userImageUri = null
    }
}