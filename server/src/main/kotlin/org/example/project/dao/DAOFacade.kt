package org.example.project.dao

import org.example.project.data.user.User


//interface DAOFacade {
//    suspend fun allAnimals(): List<Animal>
//    suspend fun animal(id: Int): Animal?
//    suspend fun addNewAnimal(type: Int, name: String, story: String): Animal?
//    suspend fun editAnimal(id: Int, name: String, story: String): Boolean
//}

interface DAOUser {
    suspend fun addUser(newUser: User): Int?
    suspend fun getUserByUserEmail(userEmail: String): User?
    suspend fun deleteUser(userId: Int): Boolean

}

interface DAOUserInfo {
    suspend fun addUser(user: User): Boolean
    suspend fun editUser(): Boolean
    suspend fun getUserById(userId: Int)
    suspend fun deleteUser(userId: Int): Boolean
}

interface DAOPet {
    suspend fun addPet()
    suspend fun getUserPets(userId: Int)
    suspend fun getPetById(petId: Int)
    suspend fun deletePet(petId: Int): Boolean
}

interface DAOPost {
    suspend fun addPost()
    suspend fun getUserPost(userId: Int)
    suspend fun deletePost(postId: Int): Boolean
}