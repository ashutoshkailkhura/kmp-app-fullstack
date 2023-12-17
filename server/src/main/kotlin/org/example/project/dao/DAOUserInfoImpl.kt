package org.example.project.dao

import data.user.User

class DAOUserInfoImpl : DAOUserInfo {
    override suspend fun addUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        TODO("Not yet implemented")
    }
}