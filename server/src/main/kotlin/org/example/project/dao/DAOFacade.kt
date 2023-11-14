package org.example.project.dao

import entity.Animal

interface DAOFacade {
    suspend fun allAnimals(): List<Animal>
    suspend fun animal(id: Int): Animal?
    suspend fun addNewAnimal(type: Int, name: String, story: String): Animal?
    suspend fun editAnimal(id: Int, name: String, story: String): Boolean
    suspend fun deleteAnimal(id: Int): Boolean
}