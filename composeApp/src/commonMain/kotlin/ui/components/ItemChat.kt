package ui.components

import Chat
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemChat(
    chat: Chat,
    onChatSelect: (userId: Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp, horizontal = 4.dp)
            .clickable(onClick = { onChatSelect(chat.userId) }),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painterResource("compose-multiplatform.xml"),
            null,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(42.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                .clip(CircleShape)
                .align(Alignment.Top),
            contentScale = ContentScale.Crop,
        )

        Text(
            chat.message,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            chat.lastTimeStamp.toString(), style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}