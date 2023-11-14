package org.example.project.dao

import entity.Animal
import kotlinx.coroutines.runBlocking
import org.example.project.dao.DatabaseFactory.dbQuery
import org.example.project.model.Animals
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToAnimal(row: ResultRow) = Animal(
        id = row[Animals.id],
        type = row[Animals.type],
        name = row[Animals.name],
        story = row[Animals.story],
    )

    override suspend fun allAnimals(): List<Animal> = dbQuery {
        Animals.selectAll().map(::resultRowToAnimal)
    }

    override suspend fun animal(id: Int): Animal? = dbQuery {
        Animals
            .select { Animals.id eq id }
            .map(::resultRowToAnimal)
            .singleOrNull()
    }

    override suspend fun addNewAnimal(type: Int, name: String, story: String): Animal? =
        dbQuery {
            val insertStatement = Animals.insert {
                it[Animals.type] = type
                it[Animals.name] = name
                it[Animals.story] = story
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAnimal)
        }

    override suspend fun editAnimal(id: Int, name: String, story: String): Boolean = dbQuery {
        Animals.update({ Animals.id eq id }) {
            it[Animals.name] = name
            it[Animals.story] = story
        } > 0
    }

    override suspend fun deleteAnimal(id: Int): Boolean = dbQuery {
        Animals.deleteWhere { Animals.id eq id } > 0
    }

}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allAnimals().isEmpty()) {
            addNewAnimal(1, "The drive to develop!", "...it's what keeps me going.")
        }
    }
}