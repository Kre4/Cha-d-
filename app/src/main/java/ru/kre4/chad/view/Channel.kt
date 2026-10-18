package ru.kre4.chad.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.kre4.chad.ui.theme.Typography

@Composable
fun Channel(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
//            .border(
//                width = 2.dp,
//                color = Color.DarkGray,
//                shape = RectangleShape)
    ) {
        Text(
            text = name,
            style = Typography.titleLarge,
            modifier = Modifier.padding(4.dp)
        )
    }

}