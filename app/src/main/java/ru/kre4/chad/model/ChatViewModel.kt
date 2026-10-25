package ru.kre4.chad.model

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kre4.chad.api.ChadApiFactory
import ru.kre4.chad.api.Message

@Stable
class ChatViewModel(
    private val channelName: String,
) : ViewModel() {

    private val api = ChadApiFactory.clientInstance

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private var hasMore = true

    init {
        loadInitial()
    }

    fun loadInitial() {
        viewModelScope.launch {
            val page = api.getMessages(
                name = channelName,
                limit = PAGE_SIZE,
                reverse = true,
            )
            _messages.value = page.sortedBy { it.id }
            hasMore = page.size >= PAGE_SIZE
        }
    }

    fun loadOlder() {
        val oldestId = _messages.value.firstOrNull()?.id ?: return
        viewModelScope.launch {
            val page = api.getMessages(
                name = channelName,
                limit = PAGE_SIZE,
                lastKnownId = oldestId,
                reverse = true,
            )
            if (page.isEmpty()) {
                hasMore = false
            } else {
                _messages.value = (page + _messages.value)
                    .distinctBy { it.id }
                    .sortedBy { it.id }
                hasMore = page.size >= PAGE_SIZE
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
