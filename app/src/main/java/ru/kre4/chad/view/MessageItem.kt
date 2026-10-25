package ru.kre4.chad.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.kre4.chad.api.ChadApiFactory
import ru.kre4.chad.api.Message
import ru.kre4.chad.ui.theme.Typography

@Composable
fun MessageItem(
    message: Message,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
    ) {
        Text(
            text = message.from,
            style = Typography.labelMedium,
        )
        val text = message.data.text?.text
        val imageLink = message.data.image?.link
        if (text != null) {
            Text(
                text = text,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        if (imageLink != null) {
            AsyncImage(
                model = ChadApiFactory.thumbUrl(imageLink),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .clickable { onImageClick(imageLink) },
            )
        }
    }
}
