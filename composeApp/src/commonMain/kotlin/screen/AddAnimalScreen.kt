package screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import entity.Animals
import kotlinx.coroutines.launch
import network.APIService

class AddAnimalScreen() : Screen {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {

        var name by remember { mutableStateOf("") }
        var story by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()

        var snackbarHostState = remember { SnackbarHostState() }
        val keyboardController = LocalSoftwareKeyboardController.current

        if (result.isNotEmpty()) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = result,
                    actionLabel = "Do something."
                )
                result = ""
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true
            )

            OutlinedTextField(
                value = story,
                onValueChange = { story = it },
                label = { Text("Story") },
                maxLines = 5,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() })
            )


            Button(onClick = {
                scope.launch {
                    val data = APIService().addAnimal(
                        Animals(
                            id = "3",
                            type = 3,
                            name = name,
                            story = story
                        )
                    )
                    result = data.toString()
                    println("XXX AddAnimalScreen $data")
                }
            }) {
                Text("Add")
            }

            Spacer(modifier = Modifier.weight(1f))

            SnackbarHost(
                hostState = snackbarHostState,
            )
        }

    }
}