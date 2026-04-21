package com.barefeet.stampid_compose.screens.loading

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.barefeet.stampid_compose.model.StampDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StampResultViewModel @Inject constructor() : ViewModel() {

    private val _userImageUri = MutableStateFlow<Uri?>(null)
    val userImageUri = _userImageUri.asStateFlow()

    private val _scanResult = MutableStateFlow<List<StampDataResponse>>(emptyList())
    val scanResult = _scanResult.asStateFlow()

    fun updateResult(data: List<StampDataResponse>) {
        _scanResult.value = data
    }

    fun updateCapturedImage(uri: Uri) {
        _userImageUri.value = uri
    }

    fun clearData() {
        _scanResult.value = emptyList()
        _userImageUri.value = null
    }
}