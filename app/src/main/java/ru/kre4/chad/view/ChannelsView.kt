package ru.kre4.chad.view

import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kre4.chad.model.ChannelsViewModel

@Composable
fun ChannelsView(
    vm: ChannelsViewModel,
    modifier: Modifier = Modifier
) {
    val posts by vm.channels.collectAsStateWithLifecycle(emptyList())
    LazyColumn(modifier) {
        items(posts) { item ->
            Channel(item)
            HorizontalDivider()
        }
    }
}