package org.example.project.dao

import org.example.project.dao.DatabaseFactory.dbQuery
import entity.User
import org.example.project.entity.UserProfileTable
import org.example.project.entity.UserTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOUserImpl : DAOUser {
    override suspend fun addUser(newUser: User): EntityID<Int>? =
        dbQuery {
            val insertUserStatement = UserTable.insert { row ->
                row[email] = newUser.userEmail
                row[password] = newUser.password
                row[salt] = newUser.salt
            }
            insertUserStatement.resultedValues?.singleOrNull()?.let {
                it[UserTable.id]
            }
        }

    override suspend fun getUserByUserEmail(userEmail: String): User? = dbQuery {
        UserTable
            .select { UserTable.email eq userEmail }
            .map {
                User(
                    it[UserTable.id].value,
                    it[UserTable.email],
                    it[UserTable.password],
                    it[UserTable.salt],
                )
            }
            .singleOrNull()
    }


    override suspend fun deleteUser(userId: Int): Boolean =
        dbQuery {
            UserTable.deleteWhere { UserTable.id eq userId } > 0
        }

}