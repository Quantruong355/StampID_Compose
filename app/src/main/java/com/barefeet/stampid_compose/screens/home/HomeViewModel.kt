package com.barefeet.stampid_compose.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barefeet.stampid_compose.data.Article
import com.barefeet.stampid_compose.utils.loadArticlesFromAssets
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class HomeUiEvent {
    object OnSearchClick : HomeUiEvent()
    object OnSettingClick : HomeUiEvent()
    data class OnArticleClick(val article: Article) : HomeUiEvent()
}

sealed class HomeUiEffect {
    object NavigateToSetting : HomeUiEffect()
    object NavigateToSearch : HomeUiEffect()
    data class NavigateToArticleDetail(val article: Article) : HomeUiEffect()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<HomeUiEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: HomeUiEvent){
        when(event){
            is HomeUiEvent.OnSearchClick -> {
                sendEffect(HomeUiEffect.NavigateToSearch)
            }
            is HomeUiEvent.OnSettingClick -> {
                sendEffect(HomeUiEffect.NavigateToSetting)
            }

            is HomeUiEvent.OnArticleClick -> {
                sendEffect(HomeUiEffect.NavigateToArticleDetail(event.article))
            }
        }
    }

    init {
        loadData()
    }

    private fun loadData() {
        // Chạy trong viewModelScope (Background Thread)
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext
            val data = loadArticlesFromAssets(context) ?: emptyList()
            _uiState.value = _uiState.value.copy(articles = data)
        }
    }

    private fun sendEffect(effect: HomeUiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }


}