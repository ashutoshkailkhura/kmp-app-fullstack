package ui.screens.home.chat

import UserInput
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.example.project.entity.WebSocketPayload
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class ChatDetailScreen(val userId: Int) : Screen {

    companion object {
        const val TAG = "ChatDetailScreen"
    }

    @Composable
    override fun Content() {

        val chatViewModel =
            getViewModel(ChatDetailScreen(userId).key, viewModelFactory { ChatViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            println("$TAG LaunchedEffect observeMsg")
            chatViewModel.observeMsg()
        }

        ChatDetailScreenContent(
            uiState = chatViewModel.chatDetailUiState,
            onBackPressed = navigator::pop,
            targetUser = userId,
            onMsgSend = { chatViewModel.sendMsg(it, userId.toString()) }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreenContent(
    uiState: ChatDetailUiState,
    onBackPressed: () -> Unit,
    targetUser: Int,
    onMsgSend: (msg: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
//                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Text("$targetUser")
            }
        },
//        contentWindowInsets = ScaffoldDefaults
//            .contentWindowInsets
//            .exclude(WindowInsets.navigationBars)
//            .exclude(WindowInsets.ime),
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection)
    ) {
        Column(
            Modifier.fillMaxSize()
                .padding(it)
                .navigationBarsPadding()
                .imePadding()
        ) {
            Messages(
                messages = uiState.msgList,
                navigateToProfile = { },
                modifier = Modifier.weight(1f),
                scrollState = scrollState
            )
            UserInput(
                onMessageSent = { content ->
                    onMsgSend(content)
                },
                resetScroll = {
//                scope.launch {
//                            scrollState.scrollToItem(0)
//                }
                },
            )
        }

    }
}

@Composable
fun Messages(
    messages: List<WebSocketPayload>,
    navigateToProfile: (String) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        reverseLayout = true,
        state = scrollState,
        modifier = modifier
    ) {
        for (index in messages.indices) {
//            val prevAuthor = messages.getOrNull(index - 1)?.author
//            val nextAuthor = messages.getOrNull(index + 1)?.author
            val content = messages[index]
//            val isFirstMessageByAuthor = prevAuthor != content.author
//            val isLastMessageByAuthor = nextAuthor != content.author

            item {
                Message(
                    onAuthorClick = { name -> navigateToProfile(name) },
                    msg = content.data,
                    isUserMe = false,
                    isFirstMessageByAuthor = false,
                    isLastMessageByAuthor = true
                )
            }
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: String,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    val borderColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    val spaceBetweenAuthors =
        if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                painterResource("compose-multiplatform.xml"),
                null,
                modifier = Modifier
                    .clickable(onClick = { })
                    .padding(horizontal = 16.dp)
                    .size(42.dp)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                contentScale = ContentScale.Crop,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(74.dp))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }
}


@Composable
fun AuthorAndTextMessage(
    msg: String,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: String) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "timestamp",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatItemBubble(
    message: String,
    isUserMe: Boolean,
) {

    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            )
        }

//        message.image?.let {
//            Spacer(modifier = Modifier.height(4.dp))
//            Surface(
//                color = backgroundBubbleColor,
//                shape = ChatBubbleShape
//            ) {
//                Image(
//                    painterResource("compose-multiplatform.xml"),
//                    null,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.size(160.dp)
//                )
//            }
//        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)