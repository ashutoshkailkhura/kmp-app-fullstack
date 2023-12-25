package ui.screens.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class ProfileScreen() : Screen {


    @Composable
    override fun Content() {
        val scrollState = rememberScrollState()

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(
                        scrollState,
                        this@BoxWithConstraints.maxHeight
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 4.dp)
                            .clickable(onClick = { }),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Edit Profile Pic",
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(Modifier.weight(1f))
                        Icon(
                            Icons.Rounded.ArrowRight,
                            contentDescription = null
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 4.dp)
                            .clickable(onClick = { }),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Edit Personal Detail",
                            modifier = Modifier
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            Icons.Rounded.ArrowRight,
                            contentDescription = null
                        )
                    }


                }
            }
        }
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun ProfileHeader(
        scrollState: ScrollState,
//        data: ProfileScreenState,
        containerHeight: Dp
    ) {
        val offset = (scrollState.value / 2)
        val offsetDp = with(LocalDensity.current) { offset.toDp() }

        Image(
            painterResource("compose-multiplatform.xml"),
            null,
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = offsetDp,
                    end = 16.dp
                )
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}