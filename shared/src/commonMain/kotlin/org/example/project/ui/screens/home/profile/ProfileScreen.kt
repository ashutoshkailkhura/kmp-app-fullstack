package org.example.project.ui.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmpproject.shared.generated.resources.Res
import kmpproject.shared.generated.resources.kodee_frightened
import org.example.project.ui.components.OnlineIndicator
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
    ) {

        // Row with user avatar and online indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
//                        .clip(CircleShape)
//                        .size(104.dp)
//                        .background(Color.White)
//                        .align(Alignment.CenterVertically)
            ) {
                Image(
                    painterResource(Res.drawable.kodee_frightened),
                    null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(80.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop,
                )

                OnlineIndicator(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onOff = true
                )
            }
        }


        // Column with clickable list items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            ClickableListItem("My Profile") {
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

            ClickableListItem("LogOut") {
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
