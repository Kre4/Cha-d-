package ru.kre4.chad.model

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun openChannel(name: String) {
        _uiState.update {
            it.copy(
                selectedChannel = name,
                fullscreenImageLink = null,
            )
        }
    }

    fun closeChannel() {
        _uiState.update {
            it.copy(
                selectedChannel = null,
                fullscreenImageLink = null,
            )
        }
    }

    fun openImage(link: String) {
        _uiState.update { it.copy(fullscreenImageLink = link) }
    }

    fun closeImage() {
        _uiState.update { it.copy(fullscreenImageLink = null) }
    }
}
