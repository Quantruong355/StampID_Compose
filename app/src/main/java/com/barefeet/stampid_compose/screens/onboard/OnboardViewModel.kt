package com.barefeet.stampid_compose.screens.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OnboardUiState(
    val pages: List<OnboardingPage> = OnboardingPageList.pages,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false
)

sealed class OnboardUiEvent {
    data class OnPageChange(val pageIndex: Int) : OnboardUiEvent()
    object OnNextClick : OnboardUiEvent()
    object OnFinishClick : OnboardUiEvent()
}

class OnboardViewModel(
//    private val onboardingManager: OnboardingManager
): ViewModel() {

    private val _uiState = MutableStateFlow(OnboardUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    fun onEvent(event: OnboardUiEvent) {
        when (event) {
            is OnboardUiEvent.OnPageChange -> {
                updatePageState(event.pageIndex)
            }

            is OnboardUiEvent.OnNextClick  -> {
                val currentPage = _uiState.value
                if (currentPage.isLastPage) {
                    onEvent(OnboardUiEvent.OnFinishClick)
                }else{
                    updatePageState(currentPage.currentPage + 1)
                }
            }

            else -> {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        viewModelScope.launch {
//            onboardingManager.saveOnboardingStatus(true)
            _navigateToHome.emit(Unit)
        }
    }

    private fun updatePageState(pageIndex: Int){
        _uiState.value = _uiState.value.copy(
            currentPage = pageIndex,
            isLastPage = pageIndex == _uiState.value.pages.size - 1
        )
    }

}