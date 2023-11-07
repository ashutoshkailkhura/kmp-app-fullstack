import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    @SerialName("category")
    val category: String? = null, // lorem
    @SerialName("content")
    val content: String? = null, // Morbi mauris torquent volutpat cum, convallis iaculis suscipit auctor. Feugiat blandit vitae quis ligula elit per. Auctor velit nostra dui proin per dui risus vestibulum quisque, aliquet dis aptent. Diam cras curae; tempus senectus integer taciti sed. Nascetur suscipit lectus netus mus risus viverra metus aliquam mattis iaculis cum. Odio.
    @SerialName("id")
    val id: Int? = null, // 15
    @SerialName("image")
    val image: String? = null, // https://dummyimage.com/800x430/b5978d/donec.png&text=jsonplaceholder.org
    @SerialName("publishedAt")
    val publishedAt: String? = null, // 15/11/2022 19:26:31
    @SerialName("slug")
    val slug: String? = null, // donec
    @SerialName("status")
    val status: String? = null, // published
    @SerialName("thumbnail")
    val thumbnail: String? = null, // https://dummyimage.com/200x200/b5978d/donec.png&text=jsonplaceholder.org
    @SerialName("title")
    val title: String? = null, // Donec quis libero gravida, placerat felis ac, lacinia augue.
    @SerialName("updatedAt")
    val updatedAt: String? = null, // 06/02/2023 08:29:58
    @SerialName("url")
    val url: String? = null, // https://jsonplaceholder.org/posts/donec
    @SerialName("userId")
    val userId: Int? = null // 15
)
