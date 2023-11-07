import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun App() {

    MaterialTheme {

        val scope = rememberCoroutineScope()

        var data: List<UserPost> by remember { mutableStateOf(listOf()) }


        LaunchedEffect(Unit) {
            scope.launch {
                data = try {
                    Greeting().greeting()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        if (data.isNotEmpty()) {
            UserPostsList(data)
        }

    }
}

@Composable
fun UserPostsList(userPosts: List<UserPost>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(userPosts) { userPost ->
            UserPostItem(userPost = userPost)
        }
    }
}


@Composable
fun UserPostItem(userPost: UserPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = userPost.title ?: "",
                maxLines = 2,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userPost.content ?: "",
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}