package ui.screens.home.createPost

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CreatePostScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow


        var postTextState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        Column(

        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = navigator::pop) {
                    Icon(Icons.Filled.Close, "Localized Description")
                }

                Spacer(modifier = Modifier.weight(1f))

                val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

                val buttonColors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = disabledContentColor
                )
                val border = if (!postTextState.text.isNotBlank()) {
                    BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                } else {
                    null
                }

                Button(
                    enabled = postTextState.text.isNotBlank(),
                    onClick = { /*TODO call api*/ },
                    colors = buttonColors,
                    border = border,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "Post")
                }
            }
            TextField(
                value = postTextState,
                onValueChange = {
                    postTextState = it
                },
                label = null,
                singleLine = false,
                maxLines = 50,
                shape = RoundedCornerShape(0.dp),
                placeholder = { Text("What do you want to talk about ?") },
                modifier = Modifier.fillMaxWidth().weight(1f).background(Color.Blue)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {/*TODO perform platform specific action*/ }) {
                    Icon(Icons.Outlined.AttachFile, "Localized Description")
                }

                Spacer(Modifier.padding(horizontal = 12.dp))

                IconButton(onClick = {/*TODO perform platform specific action*/ }) {
                    Icon(Icons.Outlined.PermMedia, "Localized Description")
                }
            }
        }
    }
}