import androidx.compose.runtime.Immutable

data class Post(
    val postId: Int,
    val userId: Int,
    val postType: Int,
    val location: String,
    val postText: String,
    val postBlob: String,
    val timestamp: Long
)

@Immutable
data class Message(
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
)


object DataUtil {

    private val postList = listOf(
        Post(
            postId = 1,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How do I put questions, report bugs, contact you, contribute, give feedback, etc.?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 2,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "What does CIO mean?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 3,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How to fix unresolved (red) Ktor imports?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 4,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "Does Ktor provide a way to catch IPC signals (e.g. SIGTERM or SIGINT) so the server shutdown can be handled gracefully?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 5,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How do I get the client IP behind a proxy?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 6,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How can I test the latest commits on main?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 7,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How can I be sure of which version of Ktor am I using?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 8,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "My route is not being executed. How can I debug it?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 9,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How to resolve 'Response has already been sent'?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        ), Post(
            postId = 10,
            userId = 1,
            postType = 1,
            location = "delhi",
            postText = "How can I subscribe to Ktor events?\uFEFF",
            postBlob = "",
            timestamp = 1674411796305
        )
    )

    val initialMessages = listOf(
        Message(
            "me",
            "Check it out!",
            "8:07 PM"
        ),
        Message(
            "me",
            "Thank you!:)",
            "8:06 PM",
            1
        ),
        Message(
            "Taylor Brooks",
            "You can use all the same stuff",
            "8:05 PM"
        ),
        Message(
            "Taylor Brooks",
            "@aliconors Take a look at the `Flow.collectAsStateWithLifecycle()` APIs",
            "8:05 PM"
        ),
        Message(
            "John Glenn",
            "Compose newbie as well, have you looked at the JetNews sample? " +
                    "Most blog posts end up out of date pretty fast but this sample is always up to " +
                    "date and deals with async data loading (it's faked but the same idea " +
                    "applies) https://goo.gle/jetnews",
            "8:04 PM"
        ),
        Message(
            "me",
            "Compose newbie: I’ve scourged the internet for tutorials about async data " +
                    "loading but haven’t found any good ones. " +
                    "What’s the recommended way to load async data and emit composable widgets?",
            "8:03 PM"
        )
    )

    fun getDummyPostList() = postList

    fun getPostDetail(id: Int) = postList.find { it.postId == id }

}