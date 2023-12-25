package ui.components

import Post
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ItemPost(
    post: Post,
    onPostSelect:(postId:Int) -> Unit,
) {

    Card(
        modifier = Modifier.padding(6.dp).clickable {
            onPostSelect(post.postId)
        },
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Text(
            post.postText,
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth().padding(6.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonWithIcon(post.location, Icons.Filled.LocationOn, false)
            Spacer(Modifier.weight(1f))
            Text(post.timestamp.toString())
        }
    }

}