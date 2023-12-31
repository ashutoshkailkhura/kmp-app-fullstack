package org.example.project.dao

import org.example.project.entity.UserProfile
import org.example.project.dao.DatabaseFactory.dbQuery
import org.example.project.entity.UserProfileTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.upsert

class DAOUserProfileImpl : DAOUserProfile {
    private fun resultRowToUserProfile(row: ResultRow) = UserProfile(
        name = row[UserProfileTable.name],
        state = row[UserProfileTable.state],
    )

    override suspend fun updateUserProfile(userId: Int, userProfile: UserProfile): EntityID<Int>? =
        dbQuery {
            val insertUserStatement = UserProfileTable.insert { row ->
                row[user] = userId
                row[name] = userProfile.name
                row[state] = userProfile.state
            }
            insertUserStatement.resultedValues?.singleOrNull()?.let {
                it[UserProfileTable.id]
            }
        }

    override suspend fun getUserProfile(userId: Int): UserProfile? =
        dbQuery {
            UserProfileTable.select {
                UserProfileTable.user eq userId
            }.map(::resultRowToUserProfile)
                .singleOrNull()
        }
}