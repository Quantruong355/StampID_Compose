package com.barefeet.stampid_compose.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class SettingUiEvent {
    object OnBackClick : SettingUiEvent()
    object OnIAPClick : SettingUiEvent()
    object OnMembershipClick : SettingUiEvent()
    object OnPrivacyClick : SettingUiEvent()
    object OnTermClick : SettingUiEvent()
}

sealed class SettingUiEffect {
    object NavigateToBack : SettingUiEffect()
    object NavigateToIAP : SettingUiEffect()
    object NavigateToMembership : SettingUiEffect()
    object NavigateToPrivacy : SettingUiEffect()
    object NavigateToTerm : SettingUiEffect()
}

class SettingViewModel: ViewModel() {

    private val _effect = Channel<SettingUiEffect>()
    val effect = _effect.receiveAsFlow()


    fun onEvent(event: SettingUiEvent){
        when(event){
            is SettingUiEvent.OnBackClick -> {
                sendEffect(SettingUiEffect.NavigateToBack)
            }
            is SettingUiEvent.OnIAPClick -> {
                sendEffect(SettingUiEffect.NavigateToIAP)
            }
            is SettingUiEvent.OnMembershipClick -> {
                sendEffect(SettingUiEffect.NavigateToMembership)
            }
            is SettingUiEvent.OnPrivacyClick -> {
                sendEffect(SettingUiEffect.NavigateToPrivacy)
            }
            is SettingUiEvent.OnTermClick -> {
                sendEffect(SettingUiEffect.NavigateToTerm)
            }
        }
    }

    private fun sendEffect(effect: SettingUiEffect){
        viewModelScope.launch {
            _effect.send(effect)
        }

    }
}