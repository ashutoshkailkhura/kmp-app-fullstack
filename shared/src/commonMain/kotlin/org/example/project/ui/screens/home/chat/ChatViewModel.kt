package org.example.project.ui.screens.home.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.AppService
import org.example.project.entity.OnlineUser
import org.example.project.entity.WebSocketEventType
import org.example.project.entity.WebSocketPayload
import org.example.project.Response

data class OnlineUserChatListUiState(
    val onlineUserList: List<OnlineUser> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class ChatDetailUiState(
    val msgList: List<WebSocketPayload> = emptyList()
)

class ChatViewModel(
    private val service: AppService
) : ViewModel() {

    companion object {
        const val TAG = "ChatViewModel"
    }

    init {
        println("$TAG init")
    }

    var onlineUserChatListUiState by mutableStateOf(OnlineUserChatListUiState())
        private set

    var chatDetailUiState by mutableStateOf(ChatDetailUiState())
        private set

    fun getOnlineUser() {
        println("$TAG getOnlineUser ")

//        viewModelScope.launch {
//            onlineUserChatListUiState = OnlineUserChatListUiState(isLoading = true)
//            onlineUserChatListUiState =
//                when (val result = sdk.remoteApi.getOnlineUser(sdk.getToken() ?: "")) {
//                    is Response.Error -> {
//                        println("$TAG getOnlineUser ${result.exception.message}")
//                        onlineUserChatListUiState.copy(
//                            isLoading = false,
//                            error = result.exception.message ?: "error"
//                        )
//                    }
//
//                    Response.Loading -> {
//                        println("$TAG getOnlineUser loading ...")
//                        onlineUserChatListUiState.copy(isLoading = true)
//                    }
//
//                    is Response.Success -> {
//                        println("$TAG getOnlineUser ${result.data.size}")
//                        onlineUserChatListUiState.copy(
//                            isLoading = false,
//                            onlineUserList = result.data
//                        )
//                    }
//                }
//        }

        viewModelScope.launch {
            onlineUserChatListUiState = OnlineUserChatListUiState(isLoading = true)
            delay(3_000)
            onlineUserChatListUiState = OnlineUserChatListUiState(
                isLoading = false, onlineUserList = listOf(
                    OnlineUser(1, 100345),
                    OnlineUser(2, 100345),
                    OnlineUser(3, 100345),
                    OnlineUser(4, 100345),
                    OnlineUser(5, 100345),
                )
            )
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
                when (val result = service.sendMessage(wsMsg)) {
                    is Response.Error -> TODO()
                    is Response.Loading -> TODO()
                    is Response.Success -> {
                        val newList = chatDetailUiState.msgList.toMutableList().apply {
                            add(0, wsMsg)
                        }
                        chatDetailUiState = chatDetailUiState.copy(
                            msgList = newList
                        )
                    }
                }
            }
        }
    }

    fun observeMsg() {
        println("$TAG observeMsg ")
        viewModelScope.launch {
            service.observeMsg()
                .collect {
                    println("$TAG observeMsg ${it.data}")
                    val newList = chatDetailUiState.msgList.toMutableList().apply {
                        add(0, it)
                    }
                    chatDetailUiState = chatDetailUiState.copy(
                        msgList = newList
                    )
                }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            service.closeChatSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
        disconnect()
    }

}
