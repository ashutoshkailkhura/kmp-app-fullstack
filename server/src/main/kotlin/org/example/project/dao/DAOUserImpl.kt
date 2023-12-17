package org.example.project.dao

import org.example.project.dao.DatabaseFactory.dbQuery
import data.user.User
import org.example.project.entity.UserTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class DAOUserImpl : DAOUser {
    override suspend fun addUser(newUser: User): Int? =
        dbQuery {
            val insertStatement = UserTable.insert { row ->
                row[userEmail] = newUser.userEmail
                row[userPassword] = newUser.password
                row[salt] = newUser.salt
            }
            insertStatement.resultedValues?.singleOrNull()?.let {
                it[UserTable.userId]
            }
        }

    override suspend fun getUserByUserEmail(userEmail: String): User? = dbQuery {
        UserTable
            .select { UserTable.userEmail eq userEmail }
            .map {
                User(
                    it[UserTable.userId],
                    it[UserTable.userEmail],
                    it[UserTable.userPassword],
                    it[UserTable.salt],
                )
            }
            .singleOrNull()
    }


    override suspend fun deleteUser(userId: Int): Boolean =
        dbQuery {
            UserTable.deleteWhere { UserTable.userId eq userId } > 0
        }

}