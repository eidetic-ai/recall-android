package ai.eidetic.common.utils

import ai.eidetic.domain.generatelessons.model.Deck
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

fun loadDataFromJsonWithId(file: File, id: String): Deck? {
    // Find all JSON files in the directory and read their contents into a list
    val jsonFiles = file.listFiles { _, name -> name.endsWith(".json") }
    val jsonStrings = jsonFiles.mapNotNull { fileName ->
        try {
            FileInputStream(fileName).use { fis ->
                InputStreamReader(fis).use { isr ->
                    isr.readText()
                }
            }
        } catch (e: IOException) {
            null
        }
    }

    // Parse the JSON strings into a list of Decks
    val gson = Gson()
    val decks = jsonStrings.mapNotNull { jsonString ->
        try {
            gson.fromJson(jsonString, Deck::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    // Return the first Deck in the list, if there is one
    return decks.firstOrNull { it.id ==  id }
}

fun loadDataFromJson(file: File): List<Deck> {
    // Find all JSON files in the directory and read their contents into a
    val jsonFiles = file.listFiles { _, name -> name.endsWith(".json") } ?: return emptyList()
    val jsonStrings = jsonFiles.mapNotNull { fileName ->
        try {
            FileInputStream(fileName).use { fis ->
                InputStreamReader(fis).use { isr ->
                    isr.readText()
                }
            }
        } catch (e: IOException) {
            null
        }
    }

    // Parse the JSON strings into a list of Decks
    val gson = Gson()
    val decks = jsonStrings.mapNotNull { jsonString ->
        try {
            gson.fromJson(jsonString, Deck::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    // Return the first Deck in the list, if there is one
    return decks
}