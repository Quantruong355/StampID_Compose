package com.barefeet.stampid_compose.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barefeet.stampid_compose.navigation.Routes
import com.barefeet.stampid_compose.screens.onboard.OnboardingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
//    private val onboardingManager: OnboardingManager
) : ViewModel() {
    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        checkNavigation()
    }

    private fun checkNavigation() {
        viewModelScope.launch {
            // Đọc từ DataStore (lấy giá trị đầu tiên rồi stop để tránh loop)
//            val isCompleted = onboardingManager.isOnboardingCompleted.first()
//
//            _startDestination.value = if (isCompleted) Routes.MainScreen else Routes.OnboardScreen
        }
    }
}