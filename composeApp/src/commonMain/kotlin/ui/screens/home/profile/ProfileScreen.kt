package ui.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.components.OnlineIndicator
import ui.screens.home.HomeScreen
import ui.screens.home.HomeViewModel
import ui.screens.home.OnLineUiState
import ui.screens.home.post.PostViewModel
import ui.screens.home.post.postList.PostListScreen

class ProfileScreen() : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val profileViewModel =
            getViewModel(ProfileScreen().key, viewModelFactory { ProfileViewModel() })

        ProfileScreenContent(
            uiState = profileViewModel.onLineUiState
        )

    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun ProfileScreenContent(uiState: Boolean) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // Row with user avatar and online indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.CenterVertically)
                ) {
                    // User avatar image or icon goes here
                    Image(
                        painterResource("compose-multiplatform.xml"),
                        null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                    )

                    OnlineIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        onOff = uiState
                    )
                }
            }


            // Column with clickable list items
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                ClickableListItem("Update Profile Detail") {
                    // Handle click action
                }

                ClickableListItem("Email Address") {
                    // Handle click action
                }

                ClickableListItem("Location") {
                    // Handle click action
                }

                ClickableListItem("Help") {
                    // Handle click action
                }
            }

        }
    }

    @Composable
    fun ClickableListItem(label: String, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }

}