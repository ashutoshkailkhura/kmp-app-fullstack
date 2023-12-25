import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
) {

    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    // Used to decide if the keyboard should be shown

    var textFieldFocusState by remember { mutableStateOf(false) }
    var lastFocusState by remember { mutableStateOf(false) }

    Surface(tonalElevation = 2.dp, contentColor = MaterialTheme.colorScheme.secondary) {

        Row(
            modifier = modifier
//                .height(72.dp)
//                .wrapContentHeight()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BasicTextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = modifier
                    .padding(4.dp)
                    .weight(1f)
                    .onFocusChanged { state ->
                        if (lastFocusState != state.isFocused) {
                            if (state.isFocused) {
                                resetScroll()
                            }
                            textFieldFocusState = state.isFocused
                        }
                        lastFocusState = state.isFocused
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                cursorBrush = SolidColor(LocalContentColor.current),
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
            )

//            val disableContentColor =
//                MaterialTheme.colorScheme.onSurfaceVariant

//            if (textState.text.isEmpty() && !textFieldFocusState) {
//                Text(
//                    modifier = Modifier
//                        .padding(start = 32.dp),
//                    text = "Type here",
//                    style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
//                )
//            }


            val border = if (!textState.text.isNotBlank()) {
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            } else {
                null
            }

            val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

            val buttonColors = ButtonDefaults.buttonColors(
                disabledContainerColor = Color.Transparent,
                disabledContentColor = disabledContentColor
            )

            // Send button
            Button(
                modifier = Modifier.height(36.dp),
                enabled = textState.text.isNotBlank(),
                onClick = {
                    onMessageSent(textState.text)
//                    // Reset text field and close keyboard
                    textState = TextFieldValue()
//                    // Move scroll to bottom
                    resetScroll()
//                    dismissKeyboard()
                },
                colors = buttonColors,
                border = border,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "Send",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}