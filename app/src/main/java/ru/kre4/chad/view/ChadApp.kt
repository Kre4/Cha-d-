package ru.kre4.chad.view

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kre4.chad.R
import ru.kre4.chad.model.ChannelsViewModel
import ru.kre4.chad.model.ChatViewModel
import ru.kre4.chad.model.MainViewModel
import ru.kre4.chad.ui.theme.Typography

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
    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeContent(
            selectedChannel = selectedChannel,
            channelsVm = channelsVm,
            onChannelClick = onChannelClick,
            onImageClick = onImageClick,
            modifier = modifier,
        )
    } else {
        PortraitContent(
            selectedChannel = selectedChannel,
            channelsVm = channelsVm,
            onChannelClick = onChannelClick,
            onBackToChannels = onBackToChannels,
            onImageClick = onImageClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun PortraitContent(
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
        OpenedChat(
            channelName = selectedChannel,
            onBackToChannels = onBackToChannels,
            onImageClick = onImageClick,
            showBackButton = true,
            modifier = modifier,
        )
    }
}

@Composable
private fun LandscapeContent(
    selectedChannel: String?,
    channelsVm: ChannelsViewModel,
    onChannelClick: (String) -> Unit,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxSize()) {
        ChannelsView(
            vm = channelsVm,
            selectedChannel = selectedChannel,
            onChannelClick = onChannelClick,
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
        )
        VerticalDivider()
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight(),
        ) {
            if (selectedChannel == null) {
                Text(
                    text = stringResource(R.string.select_chat),
                    style = Typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center),
                )
            } else {
                OpenedChat(
                    channelName = selectedChannel,
                    onBackToChannels = {},
                    onImageClick = onImageClick,
                    showBackButton = false,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun OpenedChat(
    channelName: String,
    onBackToChannels: () -> Unit,
    onImageClick: (String) -> Unit,
    showBackButton: Boolean,
    modifier: Modifier = Modifier,
) {
    val chatVm: ChatViewModel = viewModel(
        key = channelName,
    ) {
        ChatViewModel(channelName)
    }
    ChatView(
        channelName = channelName,
        vm = chatVm,
        onBackToChannels = onBackToChannels,
        onImageClick = onImageClick,
        showBackButton = showBackButton,
        modifier = modifier,
    )
}
