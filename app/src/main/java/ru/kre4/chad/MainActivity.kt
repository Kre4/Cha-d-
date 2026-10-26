package ru.kre4.chad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kre4.chad.model.ChannelsViewModel
import ru.kre4.chad.model.ChatViewModel
import ru.kre4.chad.model.MainViewModel
import ru.kre4.chad.ui.theme.ChadTheme
import ru.kre4.chad.view.ChannelsView
import ru.kre4.chad.view.ChatView
import ru.kre4.chad.view.FullscreenImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChadTheme {
                val mainVm: MainViewModel = viewModel()
                val channelsVm: ChannelsViewModel = viewModel()
                val uiState by mainVm.uiState.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val channel = uiState.selectedChannel
                    if (channel == null) {
                        ChannelsView(
                            vm = channelsVm,
                            onChannelClick = mainVm::openChannel,
                            modifier = Modifier.padding(innerPadding),
                        )
                    } else {
                        val chatVm: ChatViewModel = viewModel(
                            key = channel,
                        ) {
                            ChatViewModel(channel)
                        }
                        ChatView(
                            channelName = channel,
                            vm = chatVm,
                            onBackToChannels = mainVm::closeChannel,
                            onImageClick = mainVm::openImage,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }

                uiState.fullscreenImageLink?.let { link ->
                    FullscreenImage(
                        link = link,
                        onDismiss = mainVm::closeImage,
                    )
                }
            }
        }
    }
}
