package ui.screens.home.chat

import SharedSDK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.project.entity.OnlineUser
import org.example.project.entity.WebSocketEventType
import org.example.project.entity.WebSocketPayload
import org.example.project.netio.Response

data class OnlineUserChatListUiState(
    val onlineUserList: List<OnlineUser> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class ChatDetailUiState(
    val msgList: List<WebSocketPayload> = emptyList()
)

class ChatViewModel : ViewModel() {

    companion object {
        const val TAG = "ChatViewModel"
    }

    init {
        println("$TAG init")
    }

    private val sdk = SharedSDK

    var onlineUserChatListUiState by mutableStateOf(OnlineUserChatListUiState())
        private set

    var chatDetailUiState by mutableStateOf(ChatDetailUiState())
        private set

//    fun getLastChat() {
//        viewModelScope.launch {
//            state = when (val result = sdk.remoteApi.getMessages(sdk.getToken() ?: "")) {
//                is Response.Error -> state.copy(
//                    connected = false,
//                    isLoading = false,
//                    error = result.exception.message ?: "error"
//                )
//
//                is Response.Loading -> state.copy(isLoading = true)
//                is Response.Success -> state.copy(isLoading = false, connected = true)
//            }
//        }
//    }

    fun getOnlineUser() {
        viewModelScope.launch {
            onlineUserChatListUiState =
                when (val result = sdk.remoteApi.getOnlineUser(sdk.getToken() ?: "")) {
                    is Response.Error -> {
                        println("$TAG getOnlineUser ${result.exception.message}")
                        onlineUserChatListUiState.copy(
                            isLoading = false,
                            error = result.exception.message ?: "error"
                        )
                    }

                    Response.Loading -> {
                        println("$TAG getOnlineUser loading ...")
                        onlineUserChatListUiState.copy(isLoading = true)
                    }

                    is Response.Success -> {
                        println("$TAG getOnlineUser ${result.data.size}")
                        onlineUserChatListUiState.copy(
                            isLoading = false,
                            onlineUserList = result.data
                        )
                    }
                }
        }
    }

    fun sendMsg(msg: String, userId: String) {
        viewModelScope.launch {
            if (msg.isNotBlank()) {
                val wsMsg = WebSocketPayload(
                    type = WebSocketEventType.NEW_MESSAGE,
                    data = msg,
                    targetUserId = userId
                )
                sdk.remoteApi.sendMessage(wsMsg)
            }
        }
    }

    fun observeMsg() {
        viewModelScope.launch {
            println("$TAG observeMsg ")
//            try {
//                sdk.remoteApi.observeMsg()
//                    .onEach {
//                        println("$TAG observeMsg ${it.data}")
//                        val newList = chatDetailUiState.msgList.toMutableList().apply {
//                            add(0, it)
//                        }
//                        chatDetailUiState = chatDetailUiState.copy(
//                            msgList = newList
//                        )
//                    }
//            } catch (ex: Exception) {
//                println("$TAG observeMsg ${ex.message}")
//            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            sdk.remoteApi.closeChatSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
        disconnect()
    }

}