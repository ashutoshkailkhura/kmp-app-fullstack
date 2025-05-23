package org.example.project.dao

import org.example.project.dao.DatabaseFactory.dbQuery
import org.example.project.data.request.PostRequest
import org.example.project.entity.PostTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.example.project.entity.Post


class DAOPostImpl : DAOPost {
    private fun resultRowToPost(row: ResultRow) = Post(
        postId = row[PostTable.id].value,
        postDetail = row[PostTable.content],
        userId = row[PostTable.user].value,
        timeStamp = row[PostTable.timestamp].toString()
    )

    override suspend fun createPost(userId: Int, post: PostRequest): EntityID<Int>? =
        dbQuery {
            val createPostStatement = PostTable.insert { row ->
                row[user] = userId
                row[content] = post.content
            }
            createPostStatement.resultedValues?.singleOrNull()?.let {
                it[PostTable.id]
            }
        }

    override suspend fun getAllPost(): List<Post> =
        dbQuery {
            println("XXX : getting all post dbQuery")
            val result = PostTable.selectAll().map(::resultRowToPost)
            result
        }

    override suspend fun getPostDetail(postId: Int): Post = dbQuery {
        PostTable.select {
            PostTable.id eq postId
        }.map(::resultRowToPost).first()
    }

    override suspend fun deletePost(postId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPostOfUser(userId: Int): List<Post> =
        dbQuery {
            PostTable.select { PostTable.user eq userId }.map(::resultRowToPost)
        }
}