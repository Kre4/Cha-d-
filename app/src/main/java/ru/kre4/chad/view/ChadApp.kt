package ru.kre4.chad.view

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kre4.chad.model.ChannelsViewModel
import ru.kre4.chad.model.ChatViewModel
import ru.kre4.chad.model.MainViewModel

@Composable
fun ChadApp(
    modifier: Modifier = Modifier,
    mainVm: MainViewModel = viewModel(),
    channelsVm: ChannelsViewModel = viewModel(),
) {
    val uiState by mainVm.uiState.collectAsStateWithLifecycle()

    val imageOpen = uiState.fullscreenImageLink != null
    val chatOpen = uiState.selectedChannel != null

    BackHandler(enabled = imageOpen || chatOpen) {
        when {
            imageOpen -> mainVm.closeImage()
            chatOpen -> mainVm.closeChannel()
        }
    }

    ChadContent(
        selectedChannel = uiState.selectedChannel,
        channelsVm = channelsVm,
        onChannelClick = mainVm::openChannel,
        onBackToChannels = mainVm::closeChannel,
        onImageClick = mainVm::openImage,
        modifier = modifier,
    )

    uiState.fullscreenImageLink?.let { link ->
        FullscreenImage(
            link = link,
            onDismiss = mainVm::closeImage,
        )
    }
}

@Composable
private fun ChadContent(
    selectedChannel: String?,
    channelsVm: ChannelsViewModel,
    onChannelClick: (String) -> Unit,
    onBackToChannels: () -> Unit,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (selectedChannel == null) {
        ChannelsView(
            vm = channelsVm,
            onChannelClick = onChannelClick,
            modifier = modifier,
        )
    } else {
        val chatVm: ChatViewModel = viewModel(
            key = selectedChannel,
        ) {
            ChatViewModel(selectedChannel)
        }
        ChatView(
            channelName = selectedChannel,
            vm = chatVm,
            onBackToChannels = onBackToChannels,
            onImageClick = onImageClick,
            modifier = modifier,
        )
    }
}
