package ru.kre4.chad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.kre4.chad.model.ChannelsViewModel
import ru.kre4.chad.model.ChatViewModel
import ru.kre4.chad.ui.theme.ChadTheme
import ru.kre4.chad.view.ChannelsView
import ru.kre4.chad.view.ChatView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChadTheme {
                var selectedChannel by remember { mutableStateOf<String?>(null) }
                val channelsVm = remember { ChannelsViewModel() }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val channel = selectedChannel
                    if (channel == null) {
                        ChannelsView(
                            vm = channelsVm,
                            onChannelClick = { selectedChannel = it },
                            modifier = Modifier.padding(innerPadding),
                        )
                    } else {
                        val chatVm = remember(channel) { ChatViewModel(channel) }
                        ChatView(
                            channelName = channel,
                            vm = chatVm,
                            onBackToChannels = { selectedChannel = null },
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}
