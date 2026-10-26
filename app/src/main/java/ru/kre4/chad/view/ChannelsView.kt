package ru.kre4.chad.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kre4.chad.model.ChannelsViewModel

@Composable
fun ChannelsView(
    vm: ChannelsViewModel,
    onChannelClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val channels by vm.channels.collectAsStateWithLifecycle(emptyList())
    LazyColumn(modifier) {
        items(channels) { item ->
            Channel(
                name = item,
                onClick = { onChannelClick(item) },
            )
            HorizontalDivider()
        }
    }
}
