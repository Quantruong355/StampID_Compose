package com.barefeet.stampid_compose.screens.home

import com.barefeet.stampid_compose.data.Article

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class HomeUiEvent {
    object OnSearchClick : HomeUiEvent()
    object OnIAPClick : HomeUiEvent()
    object OnSettingClick : HomeUiEvent()
    data class OnArticleClick(val article: Int) : HomeUiEvent()
}

sealed class HomeUiEffect {
    object NavigateToSetting : HomeUiEffect()
    object NavigateToSearch : HomeUiEffect()
    object NavigateToIAP : HomeUiEffect()
    data class NavigateToArticleDetail(val article: Int) : HomeUiEffect()
}