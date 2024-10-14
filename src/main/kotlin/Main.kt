package oop.practice

import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val jsonContent = loadJsonFromResources("input.json")
    val entities = parseJson(jsonContent)
    for (entity in entities) {
        println("Entity ${entity.id} has been assigned ${classifyUniverse(entity)}")
    }
}

fun loadJsonFromResources(fileName: String): String {
    val resource =
        {}.javaClass.classLoader.getResource(fileName) ?: throw IllegalArgumentException("File not found: $fileName")

    return Files.readString(Paths.get(resource.toURI()))
}

fun parseJson(json: String): List<Entity> {
    val entities = mutableListOf<Entity>()
    val jsonObject = JSONObject(json)
    val dataArray = jsonObject.getJSONArray("data")

    for (i in 0 until dataArray.length()) {
        val item = dataArray.getJSONObject(i)

        val id = item.getInt("id")
        val age = if (item.has("age")) {
            item.getInt("age")
        } else {
            null
        }
        val isHumanoid = if (item.has("isHumanoid")) item.getBoolean("isHumanoid") else null

        val planet = if (item.has("planet")) {
            Planet.valueOf(item.getString("planet"))
        } else {
            null
        }

        val traits = if (item.has("traits")) {
            val traitsArray = item.getJSONArray("traits")
            val traitsList = mutableListOf<Trait>()
            for (j in 0 until traitsArray.length()) {
                val trait = traitsArray.getString(j)
                traitsList.add(Trait.valueOf(trait))
            }
            traitsList
        } else {
            null
        }

        val entity = Entity(id = id, isHumanoid = isHumanoid, planet = planet, age = age, traits = traits)
        entities.add(entity)
    }
    return entities
}

fun classifyUniverse(entity: Entity): String {
    return when {
        // Star Wars universe conditions
        (entity.planet == Planet.Kashyyyk || entity.planet == Planet.Endor) && ((entity.traits?.contains(Trait.HAIRY) == true) || (entity.traits?.contains(
            Trait.GREEN
        ) == true) || (entity.traits?.contains(Trait.BULKY) == true) || (entity.traits?.contains(Trait.POINTY_EARS) == true) || (entity.age
            ?: 0) > 300) -> {
            "Star Wars Universe"
        }

        // Marvel universe conditions
        (entity.planet == Planet.Earth || entity.planet == Planet.Asgard) && ((entity.traits?.contains(Trait.TALL) == true) || (entity.traits?.contains(
            Trait.BLONDE
        ) == true) || (entity.traits?.contains(Trait.EXTRA_ARMS) == true) || (entity.traits?.contains(Trait.EXTRA_HEAD) == true) || (entity.age
            ?: 0) in 0..100) -> {
            "Marvel Universe"
        }

        // Hitchhiker's universe conditions
        (entity.planet == Planet.Betelgeuse || entity.planet == Planet.Vogsphere) || ((entity.traits?.contains(Trait.GREEN) == true) || (entity.traits?.contains(
            Trait.EXTRA_HEAD
        ) == true) || (entity.traits?.contains(Trait.EXTRA_ARMS) == true) || (entity.age ?: 0) > 150) -> {
            "Hitchhiker's Universe"
        }

        // Lord of the Rings universe conditions
        ((entity.traits?.contains(Trait.HAIRY) == true) || (entity.traits?.contains(Trait.POINTY_EARS) == true) || (entity.traits?.contains(
            Trait.BULKY
        ) == true) || (entity.traits?.contains(Trait.SHORT) == true) || (entity.age ?: 0) > 500) -> {
            "Lord of the Rings Universe"
        }

        // Default case: Any remaining entities go to Lord of the Rings Universe
        else -> "Lord of the Rings Universe"
    }
}



