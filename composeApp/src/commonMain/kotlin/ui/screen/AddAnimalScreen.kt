//package ui.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.runtime.*
//import androidx.compose.material3.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.screen.Screen
//import entity.Animal
//import kotlinx.coroutines.launch
//import network.APIService
//
//class AddAnimalScreen() : Screen {
//
//    @OptIn(ExperimentalComposeUiApi::class)
//    @Composable
//    override fun Content() {
//
//        var name by remember { mutableStateOf("") }
//        var type by remember { mutableStateOf(0) }
//        var story by remember { mutableStateOf("") }
//        var result by remember { mutableStateOf("") }
//        val scope = rememberCoroutineScope()
//
//        var isButtonEnabled by remember { mutableStateOf(true) }
//
//        val snackbarHostState = remember { SnackbarHostState() }
//        val focusRequester = remember { FocusRequester() }
//        val keyboardController = LocalSoftwareKeyboardController.current
//
//        val scrollState = rememberScrollState()
//
//
//        if (result.isNotEmpty()) {
//            LaunchedEffect(snackbarHostState) {
//                keyboardController?.hide()
//                snackbarHostState.showSnackbar(
//                    message = result,
////                    actionLabel = "Do something."
//                )
//                result = ""
//            }
//        }
//
//
//        Scaffold(
//            snackbarHost = {
//                SnackbarHost(hostState = snackbarHostState)
//            },
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(12.dp)
//                    .verticalScroll(scrollState),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                OutlinedTextField(
//                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
//                    value = name,
//                    onValueChange = { name = it },
//                    label = { Text("Name") },
//                    singleLine = true,
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            // Remove focus when "Done" is pressed
//                            focusRequester.freeFocus()
//                        }
//                    ),
//                )
//
//                HorizontalSelectableChipList(type) {
//                    type = it
//                }
//
//                OutlinedTextField(
//                    modifier = Modifier.fillMaxWidth().weight(1f).focusRequester(focusRequester),
//                    value = story,
//                    onValueChange = { story = it },
//                    label = { Text("Story") },
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//                            // Remove focus when "Done" is pressed
//                            focusRequester.freeFocus()
//                            keyboardController?.hide()
//                        })
//                )
//
//
//                Button(
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = isButtonEnabled,
//                    onClick = {
//
//                        scope.launch {
//                            isButtonEnabled = false
//                            if (name.isEmpty() || story.isEmpty()) {
//                                result = "add your story"
//                                isButtonEnabled = true
//                                return@launch
//                            }
//
//                            result = try {
//                                val data = APIService().addAnimal(
//                                    Animal(
//                                        id = 3,
//                                        type = type,
//                                        name = name,
//                                        story = story
//                                    )
//                                )
//                                data.toString()
//                            } catch (ex: Exception) {
//                                ex.message.toString()
//                            }
//                            isButtonEnabled = true
//
//                        }
//                    }) {
//                    Text("Save")
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HorizontalSelectableChipList(selectedChipIndex: Int, onTypeSelect: (type: Int) -> Unit) {
//
//    val chipItems = listOf(
//        "Cow" to Icons.Default.Warning,
//        "Dog" to Icons.Default.Warning,
//        "Monkey" to Icons.Default.Warning,
//        "Cat" to Icons.Default.Warning,
//        "Bird" to Icons.Default.Warning,
//        "Other" to Icons.Default.Warning
//    )
//
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .horizontalScroll(rememberScrollState())
//    ) {
//        chipItems.forEachIndexed { index, (label, icon) ->
//            Chip(
//                text = label,
//                icon = icon,
//                isSelected = index == selectedChipIndex,
//                onChipClick = {
//                    onTypeSelect(index)
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun Chip(
//    text: String,
//    icon: ImageVector,
//    isSelected: Boolean,
//    onChipClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .padding(8.dp)
//            .clip(RoundedCornerShape(8.dp))
//            .background(if (isSelected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondary)
//            .clickable { onChipClick() }
//            .padding(8.dp)
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(
//            text = text,
//            color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
//        )
//    }
//}
