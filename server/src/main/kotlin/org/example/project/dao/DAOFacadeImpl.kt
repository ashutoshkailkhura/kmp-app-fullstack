//package org.example.project.dao
//
//import entity.Animal
//import kotlinx.coroutines.runBlocking
//import org.example.project.dao.DatabaseFactory.dbQuery
//import org.example.project.entity.AnimalTable
//import org.jetbrains.exposed.sql.ResultRow
//import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
//import org.jetbrains.exposed.sql.deleteWhere
//import org.jetbrains.exposed.sql.insert
//import org.jetbrains.exposed.sql.select
//import org.jetbrains.exposed.sql.selectAll
//import org.jetbrains.exposed.sql.update
//
//private val dao: DAOFacade = DAOFacadeImpl().apply {
//    runBlocking {
//        if (allAnimals().isEmpty()) {
//            addNewAnimal(1, "The drive to develop!", "...it's what keeps me going.")
//        }
//    }
//}
//class DAOFacadeImpl : DAOFacade {
//
//    private fun resultRowToAnimal(row: ResultRow) = Animal(
////        id = row[AnimalTable.id],
//        type = row[AnimalTable.type],
//        name = row[AnimalTable.name],
//        story = row[AnimalTable.story],
//    )
//
//    override suspend fun allAnimals(): List<Animal> = dbQuery {
//        AnimalTable.selectAll().map(::resultRowToAnimal)
//    }
//
//    override suspend fun animal(id: Int): Animal? = dbQuery {
//        AnimalTable
//            .select { AnimalTable.id eq id }
//            .map(::resultRowToAnimal)
//            .singleOrNull()
//    }
//
//    override suspend fun addNewAnimal(type: Int, name: String, story: String): Animal? =
//        dbQuery {
//            val insertStatement = AnimalTable.insert {
//                it[AnimalTable.type] = type
//                it[AnimalTable.name] = name
//                it[AnimalTable.story] = story
//            }
//            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAnimal)
//        }
//
//    override suspend fun editAnimal(id: Int, name: String, story: String): Boolean = dbQuery {
//        AnimalTable.update(where = { AnimalTable.id eq id }) {
//            it[AnimalTable.name] = name
//            it[AnimalTable.story] = story
//        } > 0
//    }
//
//    override suspend fun deleteAnimal(id: Int): Boolean = dbQuery {
//        AnimalTable.deleteWhere { AnimalTable.id eq id } > 0
//    }
//
//}
//
