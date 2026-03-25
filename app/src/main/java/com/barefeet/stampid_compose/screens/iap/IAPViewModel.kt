package com.barefeet.stampid_compose.screens.iap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class IAPPlan{ WEEK, MONTH, YEAR}

data class IAPState(
    val selectedPlan: IAPPlan = IAPPlan.YEAR,
    val packages: List<SubscriptionPackage> = emptyList(),
)

data class SubscriptionPackage(
    val plan: IAPPlan,
    val title: String,
    val price: String,
    val unit: String,
    val pricePerWeek: String? = null
)

class IAPViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(IAPState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMockBillingProducts()
    }

    private fun fetchMockBillingProducts() {
        viewModelScope.launch {
            // Giả lập delay mạng 1.5 giây
//            delay(1500)

            val mockData = listOf(
                SubscriptionPackage(
                    plan = IAPPlan.WEEK,
                    title = "Premium Weekly",
                    price = "$3.99",
                    unit = "/week"
                ),
                SubscriptionPackage(
                    plan = IAPPlan.MONTH,
                    title = "Premium Monthly",
                    price = "$9.99",
                    unit = "/month",
                    pricePerWeek = "$2.5"
                ),

                SubscriptionPackage(
                    plan = IAPPlan.YEAR,
                    title = "Premium Yearly",
                    price = "$39.99",
                    unit = "/year",
                    pricePerWeek = "$0.83"
                )
            )

            _uiState.update {
                it.copy(
                    packages = mockData,
                    selectedPlan = IAPPlan.YEAR
                )
            }
        }
    }

    fun onPlanSelected(plan: IAPPlan){
        _uiState.update { it.copy(selectedPlan = plan) }
    }
}