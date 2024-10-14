package oop.practice

import org.json.JSONObject
import java.io.File

class View(private val inputJsonPath: String) {
    fun run() {
        // Step 1: Parse JSON and classify entities
        val entities = parseJson(loadJsonFromResources(inputJsonPath))
        val classifiedEntities = classifyAllEntities(entities)

        // Step 2: Write out entities to different files based on their universe
        writeEntitiesToFiles(classifiedEntities)

        // Step 3: Print out the results
        printClassifiedEntities(classifiedEntities)
    }

    private fun classifyAllEntities(entities: List<Entity>): Map<String, List<Entity>> {
        val universeMap = mutableMapOf(
            "Star Wars Universe" to mutableListOf<Entity>(),
            "Marvel Universe" to mutableListOf<Entity>(),
            "Hitchhiker's Universe" to mutableListOf<Entity>(),
            "Lord of the Rings Universe" to mutableListOf<Entity>()
        )

        for (entity in entities) {
            val universe = classifyUniverse(entity)
            universeMap[universe]?.add(entity)
        }

        return universeMap
    }

    private fun writeEntitiesToFiles(classifiedEntities: Map<String, List<Entity>>) {
        for ((universe, entities) in classifiedEntities) {
            val filename = "${universe.replace(" ", "_").lowercase()}.json"
            File(filename).writeText(JSONObject(mapOf("entities" to entities.map { it.toJson() })).toString(2))
        }
    }

    private fun Entity.toJson(): Map<String, Any?> {
        return mapOf("id" to this.id,
            "isHumanoid" to this.isHumanoid,
            "planet" to this.planet?.name,
            "age" to this.age,
            "traits" to this.traits?.map { it.name })
    }

    private fun printClassifiedEntities(classifiedEntities: Map<String, List<Entity>>) {
        for ((universe, entities) in classifiedEntities) {
            println("Entities in $universe: ${entities.map { it.id }}")
        }
    }
}

fun main() {
    val view = View("input.json")
    view.run()
}