import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var textFieldFocusState by remember { mutableStateOf(false) }
    var lastFocusState by remember { mutableStateOf(false) }

    Surface(tonalElevation = 5.dp) {
        Row(
            modifier = modifier
//                .navigationBarsPadding()
//                .imePadding()
//                .height(64.dp)
                .wrapContentHeight()
//                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
                maxLines = 4,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp),
//                    .onFocusChanged { state ->
//                        if (lastFocusState != state.isFocused) {
//                            if (state.isFocused) {
//                                resetScroll()
//                            }
//                            textFieldFocusState = state.isFocused
//                        }
//                        lastFocusState = state.isFocused
//                    },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Default
                ),
//                cursorBrush = SolidColor(LocalContentColor.current),
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )

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
                modifier = Modifier.height(36.dp).padding(horizontal = 2.dp),
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