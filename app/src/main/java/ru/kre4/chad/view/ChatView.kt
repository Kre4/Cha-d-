package ru.kre4.chad.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import ru.kre4.chad.R
import ru.kre4.chad.model.ChatViewModel
import ru.kre4.chad.ui.theme.Typography

@Composable
fun ChatView(
    channelName: String,
    vm: ChatViewModel,
    onBackToChannels: () -> Unit,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
) {
    val messages by vm.messages.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var didInitialScroll by remember(channelName) { mutableStateOf(false) }

    LaunchedEffect(listState, messages) {
        snapshotFlow {
            listState.firstVisibleItemIndex == 0 &&
                listState.firstVisibleItemScrollOffset == 0 &&
                messages.isNotEmpty()
        }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                if (didInitialScroll) {
                    vm.loadOlder()
                }
            }
    }

    LaunchedEffect(messages) {
        if (messages.isNotEmpty() && !didInitialScroll) {
            listState.scrollToItem(messages.lastIndex)
            didInitialScroll = true
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showBackButton) {
                TextButton(onClick = onBackToChannels) {
                    Text(stringResource(R.string.back_to_chat))
                }
            }
            Text(
                text = channelName,
                style = Typography.titleLarge,
                modifier = Modifier.padding(start = 4.dp),
            )
        }
        HorizontalDivider()

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(messages, key = { it.id }) { message ->
                    MessageItem(
                        message = message,
                        onImageClick = onImageClick,
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
