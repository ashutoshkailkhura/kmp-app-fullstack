package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemChat(
    userId: Int,
    onChatSelect: (userId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onChatSelect(userId) })
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painterResource("compose-multiplatform.xml"),
                null,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(42.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop,
            )
            Text(
                "$userId",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
//        Text(
//            chat.lastTimeStamp.toString(), style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
        }
        Spacer(Modifier.height(10.dp).padding(horizontal = 6.dp).background(Color.LightGray))
    }

}