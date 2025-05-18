package ui.screens.home.post.createPost

import LocalImageProvider
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ui.components.SlideMessage
import ui.screens.home.post.CreatePostUiState
import ui.screens.home.post.PostViewModel

//import java.io.File
//import java.io.IOException
//import java.net.URL

class CreatePostScreen : Screen {

    @Composable
    override fun Content() {

        val homeViewModel =
            getViewModel(CreatePostScreen().key, viewModelFactory { PostViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        CreatePostContent(
            uiState = homeViewModel.createPostUiState,
            createPost = {
                homeViewModel.createPost(it)
            },
            onBackPress = navigator::pop,
            resetResult = {
                homeViewModel.resetResult()
            }
        )


    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun CreatePostContent(
        uiState: CreatePostUiState,
        createPost: (content: String) -> Unit,
        onBackPress: () -> Unit,
        resetResult: () -> Unit,
    ) {

//        val imageProvider = LocalImageProvider.current
//        var imageBitmap: ImageBitmap? by remember(picture) { mutableStateOf(null) }

        var postTextState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        var showSingleFilePicker by remember { mutableStateOf(false) }
        var pathSingleChosen by remember { mutableStateOf("") }
        val fileType = listOf("jpg", "png", "jpeg")

//        LaunchedEffect(picture) {
//            imageBitmap = imageProvider.getImage(picture)
//        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onBackPress()
                    }) {
                        Icon(
                            Icons.Filled.Close,
                            "Localized Description",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    val disabledContentColor =
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)

                    val buttonColors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = disabledContentColor
                    )
                    val border = if (!postTextState.text.isNotBlank()) {
                        BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                        )
                    } else {
                        BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }

                    Button(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        enabled = postTextState.text.isNotBlank(),
                        onClick = {
                            focusManager.clearFocus()
                            createPost(postTextState.text)
                        },
                        colors = buttonColors,
                        border = border,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "Post")
                    }
                }

                BasicTextField(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    value = postTextState,
                    onValueChange = {
                        postTextState = it
                    },
                    decorationBox = { innerTextField ->
                        Row(
                            Modifier
                                .padding(16.dp)
                                .focusRequester(focusRequester)
                        ) {
                            //...
                            innerTextField()
                        }
                    },
//                    label = null,
                    singleLine = false,
                    maxLines = 50,
//                    shape = RoundedCornerShape(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    ),
//                    placeholder = { Text("What do you want to talk about ?") },
                )

//                if (pathSingleChosen.isNotEmpty()) {
////                    imageBitmap?.let {
//                        Image(
//                            it,
//                            "Memory",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
////                    }
//                }

                Text("File Chosen: $pathSingleChosen")

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {/*TODO perform platform specific action*/ }) {
                        Icon(
                            Icons.Outlined.AttachFile,
                            "Localized Description",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(Modifier.padding(horizontal = 12.dp))

                    IconButton(onClick = {
                        showSingleFilePicker = true
                    }) {
                        Icon(
                            Icons.Outlined.PermMedia,
                            "Localized Description",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            if (uiState.loading) {
                keyboardController?.hide()
                CircularProgressIndicator(Modifier.size(32.dp).align(Alignment.Center))
            } else {
                Spacer(Modifier.height(32.dp).align(Alignment.Center))
            }

            SlideMessage(uiState.result) {
                if (uiState.result == "Created") {
                    onBackPress()
                } else {
                    resetResult()
                }

            }

            FilePicker(showSingleFilePicker, fileExtensions = fileType) { mpFile ->
                if (mpFile != null) {
                    pathSingleChosen = mpFile.path
                }
                showSingleFilePicker = false
            }

        }
    }
}
