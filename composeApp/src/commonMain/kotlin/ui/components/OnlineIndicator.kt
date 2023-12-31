package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnlineIndicator(onOff: Boolean) {
    val onOffColor = if (onOff) Color.Green else Color.Gray
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(Color.White, CircleShape)
            .padding(4.dp)
            .background(onOffColor, CircleShape)
    )
}